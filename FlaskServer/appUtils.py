import mysql.connector
from google.cloud import language
from google.cloud.language import enums
from google.cloud.language import types
import requests
import json

# Instantiates a client
client = language.LanguageServiceClient()
conn = mysql.connector.connect(user='mytestuser', password='mypassword', database='quotes')
query = 'SELECT * FROM quotes WHERE abs(quotes.score - %s) < 2.0 ORDER  BY RAND() LIMIT 1'
add_statement = 'INSERT IGNORE INTO quotes VALUES(%s, %s, %s, %s)'
query_cursor = conn.cursor(prepared=True)
add_cursor = conn.cursor(prepared=True)


def _score(text):
    document = types.Document(
        content=text,
        type=enums.Document.Type.PLAIN_TEXT)

    # Detects the sentiment of the text
    sentiment = client.analyze_sentiment(document=document).document_sentiment

    print('Text: {}'.format(text))
    print('Sentiment: {}, {}'.format(sentiment.score, sentiment.magnitude))
    return sentiment

def _add_to_db():
	r = requests.get('http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=10')
	print(r)
	obj = json.loads(r.text)
	for i in obj:
		add_cursor.execute(add_statement, (i['ID'], i['title'], i['content'], _score(i['content']).score))
	conn.commit()

def query_text(text):
    s = _score(text).score
    query_cursor.execute(query, (s,))
    quote = query_cursor.next()[2][3:-5]
    obj = {'quote': quote.decode('utf-8'), 'score': s}
    print(obj)
    return obj

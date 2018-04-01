import mysql.connector
from google.cloud import language
from google.cloud.language import enums
from google.cloud.language import types

# Instantiates a client
client = language.LanguageServiceClient()
conn = mysql.connector.connect(user='mytestuser', password='mypassword', database='quotes')
query = 'SELECT * FROM quotes WHERE quotes.score > %s ORDER  BY RAND() LIMIT 1'
query_cursor = conn.cursor(prepared=True)


def _score(text):
    document = types.Document(
        content=text,
        type=enums.Document.Type.PLAIN_TEXT)

    # Detects the sentiment of the text
    sentiment = client.analyze_sentiment(document=document).document_sentiment

    print('Text: {}'.format(text))
    print('Sentiment: {}, {}'.format(sentiment.score, sentiment.magnitude))
    return sentiment


def query_text(text):
    s = _score(text).score
    query_cursor.execute(query, (s,))
    _, _, quote, _ = query_cursor.next()
    obj = {'quote': quote.decode('utf-8'), 'score': s}
    print(obj)
    return obj

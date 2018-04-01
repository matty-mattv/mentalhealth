from flask import Flask, request, jsonify

from appUtils import query_text

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/quotes', methods=['GET', 'POST'])
def quotes():
    if request.method == 'POST':
        entry = request.form['entry']
    elif request.method == 'GET':
        entry = request.args.get('entry')
    else:
        raise RuntimeError('Quotes is not GET/POST somehow (contact admin to contact Flask)')
    print(type(entry))
    # return json.dumps(query_text(entry))
    return jsonify(query_text(entry))


if __name__ == '__main__':
    app.run()

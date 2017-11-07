from flask import Flask, jsonify

app = Flask(__name__)


@app.route('/<number>', methods=['GET'])
def hello_world(number)
    dictionary = {}
    if number.isdigit()
        dictionary['status'] = 'success'
        dictionary['number_squared'] = int(number)2
    else
        dictionary['status'] = 'error'
        dictionary['number_squared'] = -1
    return jsonify(dictionary)


if __name__ == '__main__'
    app.run()

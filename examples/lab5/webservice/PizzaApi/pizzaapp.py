import datetime
import uuid
import jwt
from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import func
from functools import wraps
from werkzeug.security import generate_password_hash, check_password_hash


SQLITE_URI = 'sqlite:///{path}{db_name}.db'.format(
    path='db\\',
    db_name='pizzeria'
)
MYSQL_URI = 'mysql://{username}:{password}@{hostname}/{databasename}'.format(
    username='pizzaapp',
    password='abku32po',
    hostname='pizzaapp.mysql.pythonanywhere-services.com',
    databasename='pizzaapp$pizzeria'
)

# REMOVE AND PUT APPROPRIATE ERROR CODES IN ITS PLACE !!!
ERR_CODE = 404

app = Flask(__name__)
app.config['SECRET_KEY'] = 'HPSVy8W%A>#*pK6D<)9{'
app.config['SQLALCHEMY_DATABASE_URI'] = SQLITE_URI
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

pizza_likes = db.Table(
    'pizza_likes',
    db.Column('u_id', db.Integer, db.ForeignKey('user.id', ondelete='CASCADE'), primary_key=True),
    db.Column('p_id', db.Integer, db.ForeignKey('pizza.id', ondelete='CASCADE'), primary_key=True),
)

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    open_id = db.Column(db.String(50), unique=True, nullable=False)
    name = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(100), nullable=False)
    pizzas = db.relationship('Pizza', secondary=pizza_likes, lazy='dynamic', backref=db.backref('users', lazy='dynamic'))

class Pizza(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50), unique=True, nullable=False)
    description = db.Column(db.Text, nullable=False)

def authorization_needed(fun):
    @wraps(fun)
    def decorated(*args, **kwargs):
        if 'pizza_token' not in request.headers:
            return jsonify({'msg': 'token required'}), ERR_CODE
        token = request.headers['pizza_token']
        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            this_user = User.query.filter_by(open_id=data['open_id']).first()
            return fun(this_user, *args, **kwargs)
        except jwt.DecodeError:
            return jsonify({'msg': 'invalid token'}), ERR_CODE
    return decorated

@app.route('/user', methods=['POST'])
def create_user():
    data = request.get_json()
    if 'name' not in data or 'password' not in data:
        return jsonify({'msg': 'missing header fields'}), ERR_CODE
    _name = data['name']
    _password = data['password']
    if len(_name) == 0:
        return jsonify({'msg': 'name is required'}), ERR_CODE
    if db.session.query(User.id).filter_by(name=_name).scalar() is not None:
        return jsonify({'msg': 'name is taken'}), ERR_CODE
    if len(_password) < 4:
        return jsonify({'msg': 'invalid password'})
    hash_pw = generate_password_hash(_password, method='sha256')
    db.session.add(User(open_id=str(uuid.uuid4()), name=_name, password=hash_pw))
    db.session.commit()
    return jsonify({'msg': 'success'})

@app.route('/login', methods=['GET'])
def login():
    auth = request.authorization
    if not auth or not auth.username or not auth.password:
        return jsonify({'msg': 'missing header fields'}), ERR_CODE
    user = User.query.filter_by(name=auth.username).first()
    if not user or not check_password_hash(user.password, auth.password):
        return jsonify({'msg': 'invalid credentials'}), ERR_CODE
    expire_time = datetime.datetime.utcnow() + datetime.timedelta(weeks=10)
    token = jwt.encode({'open_id': user.open_id, 'exp': expire_time}, app.config['SECRET_KEY'])
    return jsonify({'token': token.decode('UTF-8')})

@app.route('/pizza', methods=['GET'])
@authorization_needed
def get_all_pizzas_by_likes(this_user):
    pizzas = db.session.query(Pizza, func.count(pizza_likes.c.u_id).label('count')).join(pizza_likes).group_by(Pizza).order_by('count DESC')
    return jsonify( 
        {
            'pizzas': [
                {
                    'id': pizza[0].id,
                    'name': pizza[0].name,
                    'description': pizza[0].description,
                    'likes': pizza[1],
                } for pizza in pizzas
            ]
        }
    )

@app.route('/pizza', methods=['POST'])
@authorization_needed
def create_new_pizza(this_user):
    data = request.get_json()
    if 'name' not in data or 'description' not in data:
        return jsonify({'msg': 'missing header fields'}), ERR_CODE
    _name = data['name']
    _description = data['description']
    if len(_name) == 0:
        return jsonify({'msg': 'name is required'}), ERR_CODE
    if db.session.query(Pizza.id).filter_by(name=_name).scalar() is not None:
        return jsonify({'msg': 'name is taken'}), ERR_CODE
    if len(_description) == 0:
        return jsonify({'msg': 'description is required'})
    p = Pizza(name=_name, description=_description)
    p.users.append(this_user)
    db.session.add(p)
    db.session.commit()
    return jsonify({'msg': 'success'})

@app.route('/pizza/<int:pizza_id>', methods=['GET'])
@authorization_needed
def like_pizza(this_user, pizza_id):
    pizza = Pizza.query.filter_by(id=pizza_id).first()
    if not pizza:
        return jsonify({'msg': 'no such pizza'}), ERR_CODE
    pizza.users.append(this_user)
    db.session.commit()
    return jsonify({'msg': 'success'})

if __name__ == '__main__':
    app.run()
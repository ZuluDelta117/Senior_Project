import mysql.connector
import json

# Connect to MySQL database
def create_connection():
    connection = mysql.connector.connect(
        # change host, user_name, and password
        host="",
        user="",
        password="",
        database=""
    )
    return connection


##### All Login and Sign Up Functions
def login(username, password, connection= None):
    username = str(username)
    password = str(password)
    if connection == None:
        connection = create_connection()
    cursor = connection.cursor()

    filter_query = """
            SELECT user_name, password
            FROM user
            WHERE user_name = %s and password = %s;
            """
    cursor.execute(filter_query, (username, password))
    Username_Password = cursor.fetchall()

    if Username_Password == [(username, password)]:
        if connection != None:
            return "Login Successful"
        else:
            connection.close()
            return "Login Successful"

    else:
        if connection != None:
            return "Username or Password does not match"
        else:
            connection.close()
            return "Username or Password does not match"

def largest_user_id(connection):
    cursor = connection.cursor()

    user_id = """
    SELECT user_id
    FROM user
    ORDER BY user_id DESC
    LIMIT %s;
    """
    cursor.execute(user_id, (1,))
    id = cursor.fetchall()
    return int(id[0][0])


def sign_up(username, password, email):
    username = str(username)
    password = str(password)
    email = str(email)
    connection = create_connection()
    cursor = connection.cursor()

    insert_statment = """
    INSERT INTO `recipe`.`user`
    (`user_id`,
    `user_name`,
    `email`,
    `password`)
    VALUES
    (%s, %s, %s, %s)
    """

    result = login(username, password, connection)
    if result == "Login Successful":
        connection.close()
        return "Account already exists"
    else:
        user_id = largest_user_id(connection)
        user_id += 1
        cursor.execute(insert_statment, (user_id, username, email, password))
        connection.commit()
        connection.close()
        return "Sign up Successful"

##### End of All Login and Sign Up Functions

##### Start of all Recipe Page Functions

def recipe_list(connection= None):
    if connection == None:
        connection = create_connection()
    cursor = connection.cursor()

    query = """
        Select recipe, prep_time
        FROM recipes;
        """

    cursor.execute(query)
    recipes = cursor.fetchall()

    data = [dict(zip(cursor.column_names, recipe)) for recipe in recipes]
    cursor.close()
    connection.close()
    json_data = json.dumps(data)
    return json_data




##### End of all Recipe Page Functions

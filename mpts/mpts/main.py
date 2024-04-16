from flask import*
from public import*
from admin import*
from authority import*
from api import*

import uuid
import json
app=Flask(__name__,template_folder='template')

app.secret_key="jfyjgfgfy"
app.register_blueprint(public)
app.register_blueprint(admin)
app.register_blueprint(authority)
app.register_blueprint(api)



app.run(debug=True,port=5100,host="0.0.0.0")    
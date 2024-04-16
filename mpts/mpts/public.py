from flask import*
from database import*
public=Blueprint('public',__name__)



@public.route('/')
def homepage():
     return render_template("home.html")


 
@public.route('/login',methods=['post','get'])
def login():
     if 'login' in request.form:#login the value of submit button
          username=request.form["uname"]
          password=request.form["pass"]
          qry="select * from login where username='%s' and password='%s'"%(username,password)
          res=select(qry)
          session['log']=res[0]['login_id']
          
          if res[0]['usertype']=='admin':
               return redirect(url_for('admin.admin_home'))#return to the page admin home
          if res[0]['usertype']=='authority':
               qry2="select * from authority where login_id='%s'"%(session['log'])
               # res1=select(qry2)
               # if res1:
               #      session['aid']=res1[0]['authority_id']
               return redirect(url_for('authority.autho_home'))#filename.function_name
          


     return render_template("login.html")

@public.route('/reg', methods=['post','get'])
def reg():
     if 'submit' in request.form:
          name=request.form["name"]
          place=request.form["place"]
          phone=request.form["phone"]
          email=request.form["email"]
          idproof=request.form["idproof"]
          username=request.form["uname"]
          password=request.form["pass"]
          qry="insert into login values(null,'%s','%s','user')"%(username,password)
          insert(qry)
          id=insert(qry)
          qryi="insert into user values(null,'%s','%s','%s','%s','%s','%s')"%(id,name,place,phone,email,idproof)
          insert(qryi)
          return'''<script>alert('Registred');window.location="/"</script>'''
     
     return render_template('reg.html')




     
          
          
     
     
     
     
     
     

          
          
          
          
          
   
from flask import*
from database import*
authority=Blueprint('authority',__name__)

@authority.route('/authority_home')
def autho_home():
    return render_template('autho_home.html')

@authority.route('/view',methods=['post','get'])
def autho_profile():
    data={}
    qry="select * from authority where login_id='%s'"%(session['log'])
    data['view']=select(qry)
    
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None
        
        
    if action=='update':
        a="select * from authority where login_id='%s'"%(id)
        data['up']=select(a)
        
        
    if 'update' in request.form:
        
        
        name=request.form['name']
        desi=request.form['desi']
        phone=request.form['phone']
        mail=request.form['mail']
        idproof=request.form['idproof']
        qry="update authority set name='%s',designation='%s',phone='%s',email='%s',id_proof='%s' where login_id='%s'"%(name,desi,phone,mail,idproof,session['log'])
        update(qry)
        return'''<script>alert(' profile updated');window.location="/view"</script>'''
        
    return render_template('autho_profile.html',data=data)


@authority.route('/send_comp',methods=['get','post'])
def send_comp():
    return render_template('')






# @authority.route('/update_authoform',methods=['post','get'])#
# def update_authoform():
    
#     data={}
#     qry="select * from authority  where login_id='%s'"%(session['log'])
#     res=select(qry)
    
#     data['view']=res
#     if 'update' in request.form:
#         name=request.form['name']
#         desi=request.form['desi']
#         phone=request.form['phone']
#         mail=request.form['mail']
#         idproof=request.form['idproof']
        
        
#         qry="update authority set name='%s',designation='%s',phone='%s',email='%s',id_proof='%s' where login_id='%s'"%(name,desi,phone,mail,idproof,session['log'])
#         update(qry)
#         # return'''<script>alert('updated');window.location="/authority_home"</script>'''
    
#     return render_template("autho_profile.html",data=data)
    


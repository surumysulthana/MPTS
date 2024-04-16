from flask import*
from database import*
admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
    return render_template('admin_home.html')




@admin.route('/admin_autority',methods=['post','get'])
def admin_autho():
 
  
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='delete':
        qryd="delete from authority where authority_id='%s'"%(id)
        delete(qryd)
        return'''<script>alert('deleted');window.location="/admin_autority"</script>'''
    if action=='accept':
        qrya="update login  set usertype='Authority' where usertype='pending'"
        update(qrya)
        return'''<script>alert('accepted');window.location="/admin_autority"</script>'''
    data={}
    qry="SELECT * FROM authority INNER JOIN login USING(login_id)"
    data['view']=select(qry)    
    
    
        
    return render_template('manage_autority.html',data=data)




# @admin.route('/update_form',methods=['post','get'])
# def update_form():
#     id=request.args['id']
#     data={}
#     qry="select * from authority  where authority_id='%s'"%(id)
#     res=select(qry)
    
#     data['view']=res
#     if 'update' in request.form:
#         name=request.form['name']
#         desi=request.form['desi']
#         phone=request.form['phone']
#         mail=request.form['mail']
#         idproof=request.form['idproof']
        
        
#         qry="update authority set name='%s',designation='%s',phone='%s',email='%s',id_proof='%s' where authority_id='%s'"%(name,desi,phone,mail,idproof,id)
#         update(qry)
#         return'''<script>alert('updated');window.location="/admin_autority"</script>'''
    
#     return render_template("update.html",data=data)




@admin.route('/requests',methods=['post','get'])
def requests():
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='delete':
        qryd="delete from permit_request where permit_request_id='%s'"%(id)
        delete(qryd)
        return'''<script>alert('deleted');window.location="/requests"</script>'''
    if action=='accept':
        qrya="update permit_request  set status='accept' where status='pending'"
        update(qrya)
        return'''<script>alert('accepted');window.location="/requests"</script>'''
    data={}
    qry="select * from permit_request inner join user using(user_id)"
    data['view']=select(qry) 
   
    return render_template('view_request.html',data=data)



@admin.route('/viewuser')
def viewuser():
    
    data={}
    qryv="select * from user"
    data['views']=select(qryv)
    return render_template('view_user.html',data=data)
    


@admin.route('/complaint',methods=['post','get'])
def complaint():
    data={}
    cqry="select * from complaints "
    data['view']=select(cqry)
    
        
    
    return render_template("complaint.html",data=data)


@admin.route('/admin_reply',methods=['get','post'])
def replay():
    id=request.args['id']
    if 'rep' in request.form:
        replay=request.form['reply']
        qryr="update complaints set reply='%s'  where complaint_id='%s'"%(replay,id)
        update(qryr)
        return'''<script>alert('complaint replaid');window.location="/complaint"</script>'''
        
        
        
        

    return render_template("reply.html")


           
@admin.route('/noti',methods=['post','get'])
def send_noti():
    if 'send' in request.form:
        title=request.form['title']
        desc=request.form['desc']
        date=request.form['date']
        
        qry="insert into notification values(null,'%s','%s','%s')"%(title,desc,date)
        insert(qry)
        return'''<script>alert('notification send');window.location="/noti"</script>'''
        
        
    return render_template("admin_noti.html")


    




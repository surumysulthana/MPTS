import shutil
from flask import*
from database import*
import qrcode
import json

import uuid


api=Blueprint('api',__name__)


@api.route('/autholog')
def autholog():
    data={}
    username=request.args["username"]
    password=request.args["password"]
          
    qry="select * from login where username='%s' and password='%s'"%(username,password)
    res=select(qry)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        
    return str(data) 


@api.route('/authoreg')
def authoreg():
    data={}
    name=request.args["username"]
    desi=request.args["designation"]
    phone=request.args["phone"]
    email=request.args["mail"]
    idproof=request.args["id"]
    username=request.args["uname"]
    password=request.args["pswd"]
    qry="insert into login values(null,'%s','%s','pending')"%(username,password)
    insert(qry)
    id=insert(qry)
    qryi="insert into authority values(null,'%s','%s','%s','%s','%s','%s')"%(id,name,desi,phone,email,idproof)
    res=insert(qryi)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
     
    return str(data)


@api.route('/authoprofile')
def authoprofile():
    data={}
    lid=request.args["id"]
    qry="select * from authority where login_id='%s'"%(lid)
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'
     
    return str(data)


@api.route('/authocomp')
def authocomp():
    data={}
    loginid=request.args['loginid']
    title=request.args["title"]
    des=request.args["feed"]
    

    
    qryi="insert into complaints values(null,'%s','%s','%s','pending',curdate())"%(loginid,title,des)
    res=insert(qryi)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='send'
    return str(data)








@api.route('/reply')
def reply():
    data={}
    loginid=request.args['lid']
  
    qryi="select * from  complaints where sender_id='%s'"%(loginid)
    res=select(qryi)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'
    
    
    return str(data)


@api.route("/editprofile")
def editprofile():
    data={}
    lid=request.args["lid"]
    qry="select * from authority  where login_id='%s'"%(lid)
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
     
    return str(data)

@api.route("/update")
def update_profile():
    data={}
    lid=request.args["lid"]
    name=request.args["username"]
    desi=request.args["designation"]
    phone=request.args["phone"]
    email=request.args["mail"]
    idproof=request.args["id"]
    qry="update authority set name='%s',designation='%s',phone='%s',email='%s',id_proof='%s' where login_id='%s'"%(name,desi,phone,email,idproof,lid)

    res=update(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        
    
     
    return str(data)

@api.route("/notification")
def noti():
    data={}
    # lid=request.args["lid"]
    qry="select * from notification "
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
     
    return str(data)


@api.route("/autho_reg",methods=['get','post'])
def autho_reg():
    data={}
    name=request.form["fname"]
    plc=request.form["place"]
    phone=request.form['phone']
    email=request.form["email"]
    idproof=request.files["pdf"]
    username=request.form["uname"]
    password=request.form["pass"]
    
    qry="insert into login values(null,'%s','%s','Authority')"%(username,password)
    id=insert(qry)
    
    path="static/"+str(uuid.uuid4())+idproof.filename
    idproof.save(path)
    
    qryi="insert into authority values(null,'%s','%s','%s','%s','%s','%s')"%(id,name,plc,phone,email,path)
    res=insert(qryi)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
    return str(data)

@api.route("/user_reg",methods=['get','post'])
def userreg():
    data={}
    name=request.form["fname"]
    plc=request.form["place"]
    phone=request.form['phone']
    email=request.form["email"]
    idproof=request.files["pdf"]
    username=request.form["uname"]
    password=request.form["pass"]
    
    qry="insert into login values(null,'%s','%s','user')"%(username,password)
    id=insert(qry)
    
    path="static/"+str(uuid.uuid4())+idproof.filename
    idproof.save(path)
    
    qryi="insert into user values(null,'%s','%s','%s','%s','%s','%s')"%(id,name,plc,phone,email,path)
    res=insert(qryi)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
    return str(data)
    
    
@api.route("/userreq")
def userreq():
    data={}
    loginid=request.args['loginid']
    title=request.args["title"]
    reason=request.args["reason"] 
    fd=request.args["fd"]
    ft=request.args["ft"]
    fl=request.args["fl"]
    tl=request.args["tl"]
    image=request.args["image"] 
    qryr="insert into  permit_request values(null,(select user_id from user where login_id='%s'),'%s','%s','%s','%s','%s','%s','pending','%s',curdate(),'pending')"%(loginid,title,reason,fd,ft,fl,tl,image)
    res=insert(qryr)
    
    if res:
        
        o="select * from permit_request where permit_request_id='%s'"%(res)
        e=select(o)
        print(e)
        if e:
            session['name']=e[0]['title']
        qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
        )
        qr.add_data(res)
        qr.make(fit=True)

        # Create an image of the QR code
        img = qr.make_image(fill_color="black", back_color="white")
        rr="static/qr_image/"+ session['name'] +".png" # Save the QR code image to a file
        img.save(rr)
        print("--------------------")
        up="update permit_request set qr_code='%s' where permit_request_id='%s'"%(rr,res)
        update(up)
    
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    
    return str(data)
    

    

    
@api.route("/user_send_complaint")
def user_send_complaint():
    data={}
    feed=request.args['feed']
    id=request.args['id']
    title=request.args['title']

    qry="insert into complaints values(null,'%s','%s','%s','pending',curdate())"%(id,title,feed)
    res=insert(qry)
    
    if res:
        data['status']='success'
    else:
        data['status']='failed'
    data['method']='send' 
    return str(data)


@api.route("/user_view_complaint")
def user_view_complaint():
    data={}
    id=request.args['id']
    qry="select * from complaints where sender_id='%s'"%(id)
    res=select(qry)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        
    data['method']='view'
    return str(data)




@api.route("/user_view_noti")
def user_view_noti():
    data={}
    qry="select * from notification"
    res=select(qry)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        
    data['method']='view'
    return str(data)


@api.route("/user_view_request")
def user_view_request():
    data={}
    id=request.args['id']
    qry = "SELECT * FROM permit_request WHERE user_id=(SELECT user_id FROM USER WHERE login_id='%s') AND STATUS='accept'" % (id,)
    # qry="SELECT * FROM permit_request WHERE user_id=(SELECT user_id FROM USER WHERE login_id='%s') AND STATUS='accept'"%(id)
    res=select(qry)
    
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        
    data['method']='view'
    return str(data)

from core import *
@api.route("/user_send_request",methods=['get','post'])
def user_send_request():
    data={}
    id=request.form['id']
    title=request.form['title']
    reason=request.form['reason']
    f_date=request.form['f_date']
    f_time=request.form['f_time']
    f_location=request.form['f_location']
    t_location=request.form['t_location']
    route=request.form['route']

    image=request.files['image']
    
    path="static/"+str(uuid.uuid4())+image.filename
    image.save(path)
    
    qry="insert into permit_request values(null,(select user_id from user where login_id='%s'),'%s','%s','%s','%s','%s','%s','%s','pending','%s',curdate(),'pending')"%(id,title,reason,f_date,f_time,f_location,t_location,route,path)
    res=insert(qry)
    if res:
        o="select * from permit_request where permit_request_id='%s'"%(res)
        e=select(o)
        print(e)
        if e:
            session['name']=e[0]['title']
        qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
        )
        qr.add_data(res)
        qr.make(fit=True)

        # Create an image of the QR code
        img = qr.make_image(fill_color="black", back_color="white")
        rr="static/qr_image/"+ session['name'] +".png" # Save the QR code image to a file
        img.save(rr)
        print("--------------------")
        up="update permit_request set qr_code='%s' where permit_request_id='%s'"%(rr,res)
        update(up)
        qry1="select * from user where user_id=(select user_id from permit_request where permit_request_id='%s')"%(res)
        res1=select(qry1)
        bid=res1[0]['user_id']
        name=res1[0]['name']
        
        pid = str(bid)
        train_images_folder = os.path.join("static", "trainimages", pid)
        
        if not os.path.isdir(train_images_folder):
                os.makedirs(train_images_folder)

            # Copy the image to the trainimages folder three times with different names
        for i in range(1, 4):  # Loop three times
            destination_image_path = os.path.join(train_images_folder, f"{name}_{i}.jpg")
            shutil.copy(path, destination_image_path)
            
        enf("static/trainimages/")
    
        data['status']='success'
    else:
        data['status']='failed'
        
    data['method']='view'
    return str(data)

from geopy.geocoders import Nominatim

@api.route("/scan")
def scan():
    id=request.args['id']
    data={}
    qry="select * from permit_request where permit_request_id='%s'"%(id)
    res=select(qry)
    print(res,"/////////")
    

    def get_coordinates(location):
        geolocator = Nominatim(user_agent="geo_app")
        location_data = geolocator.geocode(location)

        if location_data:
            return location_data.latitude, location_data.longitude
        else:
            return None, None

    # Example usage:
    location = res[0]['to_location']
    latitude, longitude = get_coordinates(location)

    if latitude is not None and longitude is not None:
        print(f"Latitude: {latitude}, Longitude: {longitude}")
    else:
        print("Location not found.")
        
        
    if res:
        data['status']='success'
        data['lati']=latitude
        data['longi']=longitude
    else:
        data['status']='failed'
        
    return str(data)

@api.route("/auth_view_request")
def auth_view_request():
    data={}
    id=request.args['id']
    qry="select * from permit_request inner join user using(user_id) where permit_request_id='%s'"%(id)
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['method']='view'  
    return str(data)


@api.route("/auth_send_pic",methods=['get','post'])
def auth_send_pic():
    data={}
    image=request.files['image']
    id=request.form['id']
    path="static/test.jpg"
    image.save(path)
    print(path)
    result=val(path)
    
    qry="select user_id from permit_request where permit_request_id='%s'"%(id)
    res=select(qry)
    
    if result:
        for i in result:
            print("^^^^^^^^^")
            if int(res[0]['user_id']) == int(i):
                data['data']='matching'
            else:
                data['data']='not matching'
    else:
        data['data']='not matching' 
    data['status']='success'       
    print(result)
    data['method']='check'
    print(data,"**************")
    return str(data)










#######################################################################
#######################################################################
#######################################################################
#######################################################################
#######################################################################


from geopy.geocoders import Nominatim
from geopy.distance import geodesic


def get_coordinates(location):
    geolocator = Nominatim(user_agent="geo_app")
    location_data = geolocator.geocode(location)

    if location_data:
        print(location_data,'lololo'*100)
        return location_data.latitude, location_data.longitude
    
        
    else:
        return None

def is_within_radius(current_coords, permitted_coords,radius_km=30):
    if current_coords is None:
        return False

    for coords in permitted_coords:
        if coords is None or len(coords) != 2:
            continue  # Skip invalid coordinates

        distance = geodesic(current_coords, coords).kilometers
        if distance <= radius_km:
            return True

    return False


    
from flask import jsonify

@api.route('/checklocation', methods=['GET', 'POST']) 
def checklocation():
    data = {}
    
    current_location = request.args.get('curplace')
    # current_location="thalore"
    
    perid = request.args.get('perid')
    
    qry = "SELECT route FROM permit_request WHERE permit_request_id='%s'" % perid
    res = select(qry)
    ab = res[0]
    
    preferred_route = ab['route'].split(',')
    permitted_coords = [get_coordinates(location) for location in preferred_route]
    current_coords = get_coordinates(current_location)
    print(current_location,"high"*15)
    
    if is_within_radius(current_coords, permitted_coords, radius_km=30):
        result = "Inside the permitted route within 30 kilometers."
        data['status'] = "sucess"
        data['method'] = 'within'
       
    else:
        result = "Outside the permitted route or beyond 30 kilometers."
    
        data['status'] = "sucess"
        data['method'] = 'within'
    data['data'] = result
        
    return jsonify(data)



####################################################
####################################################
####################################################
####################################################
####################################################
####################################################


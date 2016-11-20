from __future__ import print_function
import pymysql
import sys

conn = pymysql.connect(host='localhost', user='skim', password='tjddnr', db='test_database', charset = 'utf8')

curs = conn.cursor()


sql = "select * from history where uid=\"hean12340302\" order by date desc"
curs.execute(sql)
rows = curs.fetchall()
#print(rows)

d=0
xx=len(rows)
print(len(rows))

print(rows[1])
d=1
date=str(unicode(rows[d][0]))

while(d<xx):
	# print(rows[d])
	# print(str(unicode(rows[0][0])))
	# print(str(rows[0][1]))
	# print(str(rows[0][2]))
	# print(str(rows[0][3]))

	date=str(unicode(rows[d][0]))
	uid=str(rows[d][1])
	foodname=str(rows[d][2])
	quant =float(rows[d][3])
	u='_'

	sql = "select calorie,carbohydrate,protein,fat,fiber,vita_c,ca,na,fe from nutri_info where d_name=\""+foodname+"\""
	curs.execute(sql)
	rows2 = curs.fetchall()
	irows=list()

	for i in rows2[0]:
		irows.append(float(i))

	mrows=list()
	for i in irows:
		mrows.append(float("{0:.2f}".format(i*quant)))


	print(uid+u+date+u+foodname+u+str(quant)+u,end='')


	for i in mrows:
		print(str(i), end='')
		print('_', end='')

	d+=1
	


# sql = "select uid,date,history.d_name,unit,calorie,carbohydrate,protein,fat,fiber,vita_c,ca,na,fe from history join nutri_info where history.d_name=nutri_info.d_name;"
# print('sql is')
# print(sql) 
# curs.execute(sql)
# rows = curs.fetchall()
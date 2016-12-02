import pymysql

conn = pymysql.connect(host='localhost', user='skim', password='tjddnr', db='test_database', charset = 'utf8')

curs = conn.cursor()

sql = "select * from member_info"
curs.execute(sql)

rows = curs.fetchall()

print(rows)

while 1<2:
	pass


conn.commit()
conn.close()

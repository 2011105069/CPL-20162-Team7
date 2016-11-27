import subprocess

def label_image(img):
	#call lab_image.py
	LabScript = "/tf_files/label_image.py"
	print(LabScript + ' ' + img)

	batcmd = 'sudo python ' + LabScript + ' ' + img
	result = subprocess.check_output(batcmd, shell=True)
	print('--------------')
	print(result)
	res = result.split('(')[0]
	s = result.split('=')[1]
	s = s.split(')')[0]
	res += s
	res = res.replace('  ','-')
	food = res.split('-')[0]

	#print('Result is : ' + food)

	if food == 'hamburger':
		return 1
	else:
		print("")
		print('wrong!! ' + img)
		print('')
		return 0

	pass

img = '/home/swkim306/Desktop/CPL-20162-Team7/hamburger_testSet/ht'
i = 1
x = '{:03d}'.format(i)
z = '.JPG'


img2= img + '{:03d}'.format(i) + z

#label_image(img2)
ss=0

while i<101:
	img2= img + '{:03d}'.format(i) + z
	ss += label_image(img2)
	i+=1


print(ss)
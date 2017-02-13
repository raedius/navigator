create drive root
root created successfully
create folder f1
Error: Path is a required parameter for Create
create folder f1 root
f1 created successfully
create folder f2 root
f2 created successfully
create text t1 root\f1
t1 created successfully
list
*root
**f1
***t1
	()
	()
**f2
	()
move root\f1\t1 root\f2
root\f1\t1 moved to root\f2
list
*root
**f1
	()
**f2
***t1
	()
	()
move root\f2 root\f1
root\f2 moved to root\f1
list
*root
**f1
***f2
****t1
	()
	()
	()
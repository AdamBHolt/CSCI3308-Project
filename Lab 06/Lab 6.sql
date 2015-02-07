1.
SELECT * from store ORDER BY Name;
SELECT * from store ORDER BY Name LIMIT 3;
SELECT * from store ORDER BY Name DESC LIMIT 3;

2.
SELECT * from store WHERE PRICE>1;

3.
SELECT *, Qty * Price AS "Extended Price" from store;

4.
SELECT SUM(Qty * Price) AS "Total Cost" from store;

5.
SELECT COUNT(*) AS "Total Items" FROM store;

6.
SELECT * from Course;

7.
SELECT * FROM Course WHERE deptId=1;

8.
SELECT SUM(count) AS "Total Enrollment" FROM Enrollment;

9.
SELECT COUNT(*) AS "Total Classes" FROM Course;

10.
SELECT Dept.Name AS "Dept", Course.Name AS "Course" FROM Course JOIN Dept ON Dept.Id=Course.deptId;

11.
SELECT CONCAT(Dept.Name, Course.Name) AS "Course" FROM Course JOIN Dept ON Dept.Id=Course.deptId;

12.
Select * FROM Course JOIN Dept ON Dept.Id=Course.deptId JOIN Enrollment ON Enrollment.Id=Course.Id;

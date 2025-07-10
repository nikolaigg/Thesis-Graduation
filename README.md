**Overview**
Web application built with Java Spring Boot, Thymeleaf, and PostgreSQL designed to manage thesis submissions, defenses, and grading workflows.

**Preloaded Data**
The preloaded sql data only preloads the admin user with the following login information:

**username**: admin

**password**: admin

This admin user has full access to create, update and delete departments, students and teachers, and assign head of department.

**Teacher features**
- Teachers can upload a thesis application for a student in the department
- Teachers can approve or reject applications of other teachers
- Teachers can write a review for students theses, whose supervisor they are not
- Assigned head of department can schedule theses defenses
- Assigned committee can grade students' theses
  
**Student features**
- Students can view information about their application, thesis and grading
- Students can upload their thesis after approved

# A Blog For Adding Articles

![Project Image](./img/img.png)

---

## Description

- A Java Web Application (2.5) For Adding Articles On Legendary Monsters

#### Fonctionnalites

- Crud Blog Posts
- Post Pagination
- Multi Lang (fr/eng/ger)
- File Upload (img jpg using blob)
- Send Email requires config [conf](#conf)
- Login
- Encrypt Pass

---

#### Technologies

- Java (jsp/servlet/jdbc)
- MongoDb
- Bootswatch Darkly Theme

---

#### conf

- You Configure The Data Base In com.Conn
- Mail Config

1. Create a Gmail Acount And Activate Activate less Secure App To Send Email Using SMTP
2. Change In The Packg com.controller in the method SendMailhtml String username = "xxxxx@gmail.com";
   String password = "xxxxxx";

#### Installation

1. Execute sql.sql scripte To Create the Data Base
2. Import The Project Or Deploy The war

---

#### Tools

- Eclipse
- Xammp

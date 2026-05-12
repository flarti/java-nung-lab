# VDS Platform – Virtual Dedicated Server Platform


## 📌 Опис проекту

**VDS Platform** – це навчальний веб-застосунок для управління віртуальними виділеними серверами.  
Проєкт створений з метою вивчення:

- Java (Servlet API)
- Hibernate (ORM)
- PostgreSQL
- Maven
- Конфігурації через properties
- Структурування Java Web-проєктів

Застосунок демонструє класичну багаторівневу архітектуру з розділенням відповідальності між шарами.

---

## 🏗 Структура проекту

Проєкт побудований відповідно до стандартної структури Maven:

```
vdsplatform
│
├── pom.xml
│
└── src
└── main
├── java
│ └── ua.edu.nung.fit.vdsplatform
│ ├── controller/ # Контролери (Servlets)
│ ├── dao/ # Доступ до бази даних
│ ├── model/ # Сутності (Entity класи)
│ ├── service/ # Бізнес-логіка
│ └── util/ # Допоміжні класи (HibernateUtil)
│
├── resources
│ ├── hibernate.cfg.xml
│ ├── project.properties
│ └── project.properties.example
│
└── webapp
└── WEB-INF
└── web.xml

```

---

## 🔐 Файл `project.properties`

Файл `project.properties` містить усі конфігураційні параметри застосунку, включаючи:

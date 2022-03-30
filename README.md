## UserCRUD
사용자 정보를 생성(C), 조회(R), 수정(U), 삭제(D) 하는 REST API
<br><br><img src="https://user-images.githubusercontent.com/97152402/160889777-8a580323-4db7-45a5-9cc1-fcb01ee92a88.png" width="800" height="400">


### Environment and Skills
- JDK 11
- Spring Boot 2.6.5 (Gradle)
- Tomcat
- Oracle
- Mybatis
- REST API
- Json
- InteliJ

### DDL
```
CREATE TABLE user_info(
    login_id VARCHAR(20) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    user_address VARCHAR(200) NOT NULL,
    user_phone VARCHAR(20) NOT NULL,
    PRIMARY KEY(login_id)
);
```

### Build
1. 프로젝트 폴더(~\UserCRUD)로 이동
2. **./gradlew build** 실행
<br><img src="https://user-images.githubusercontent.com/97152402/160893082-96696d7d-787a-4dff-b464-1bbd7a5e165c.png" width="500" height="80">

### Run
1. ~\UserCRUD\build\libs 로 이동
2. **java -jar UserCRUD-0.0.1-SNAPSHOT.jar** 실행
<br><img src="https://user-images.githubusercontent.com/97152402/160894200-cbc56e32-ac93-4e5d-8682-b6c409c29d45.png" width="900" height="200">


### Test
#### Create User
POST http://localhost:8080/user
<br>request :
```
{
    "loginId" : "eb-silverstar",
    "userName" : "은별",
    "userAddress" : "경기도 성남시 분당구 333-45",
    "userPhone" : "01011112222"
}
```
response :
```
{
    "status": "success",
    "response": "eb-silverstar 사용자를 생성하였습니다.",
    "errorMessage": null
}
```
result :
<br><img src="https://user-images.githubusercontent.com/97152402/160895822-1c4828bd-44e1-4064-9f96-c10911faf613.png" width="550" height="50">

#### Read User
GET http://localhost:8080/user/{loginId}
<br>
response :
```
{
    "status": "success",
    "response": {
        "loginId": "eb-silverstar",
        "userName": "은별",
        "userAddress": "경기도 성남시 분당구 333-45",
        "userPhone": "01011112222"
    },
    "errorMessage": null
}
```

#### Update User
PUT http://localhost:8080/user
<br>request :
```
{
    "loginId" : "eb-silverstar",
    "userName" : "수정",
    "userAddress" : "경기도 수원시 광교동 440",
    "userPhone" : "01055556666"
}
```
response :
```
{
    "status": "success",
    "response": "eb-silverstar 사용자 정보를 수정하였습니다.",
    "errorMessage": null
}
```
result :
<br><img src="https://user-images.githubusercontent.com/97152402/160896645-f484ebad-af54-4a71-ae47-4b925110bf93.png" width="550" height="50">

#### Delete User
DELETE http://localhost:8080/user/{loginId}
<br>response :
```
{
    "status": "success",
    "response": "eb-silverstar 사용자 정보를 삭제하였습니다.",
    "errorMessage": null
}
```
result :
<br><img src="https://user-images.githubusercontent.com/97152402/160897480-e9d49fa0-70a7-430e-9003-f793556b8c87.png" width="400" height="50">

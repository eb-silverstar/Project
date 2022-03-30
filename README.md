## RandomMaster
<a href="https://github.com/eb-silverstar/RandomMaster">RandomMaster</a>로부터 데이터를 전송 받아 새로운 DB에 Insert 합니다.
<br><br><img src="https://user-images.githubusercontent.com/97152402/160886182-62037a99-18f0-4518-88c1-8bf5a526186d.png" width="900" height="350">


### Environment
- JDK 11
- Oracle 18c
- InteliJ

### DDL
```
CREATE TABLE random_slave(
    id NUMBER NOT NULL,
    random_number NUMBER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
);
```

### DB Setting (in source)
```
public static Connection dbConnect() {
    String jdbcUrl = "jdbc:oracle:thin:@[ipAdress]:[port]:[name]";
    String id = "[userId]";
    String password = "[userPassword]";
    .
    .
}
```

### Build
[IntelliJ] **Build** > **Build Artifacts...** > **Build**

### Test
1. ~\RandomSlave\out\artifacts\RandomSlave_jar 로 이동
2. **java -jar RandomSlave.jar [SeverIPAddress]** 실행
<br><br><img src="https://user-images.githubusercontent.com/97152402/160886752-e4296956-fdce-4ce4-ad78-7840a03e6adf.png" width="450" height="500"> <img src="https://user-images.githubusercontent.com/97152402/160887069-a562c1ed-b84b-42ff-96cc-78e01becfddb.png" width="450" height="500">

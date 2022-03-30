## RandomMaster
<a href="https://github.com/eb-silverstar/RandomCreator">RandomCreator</a>로 생성한 Data를 DB에서 읽어와 접속한 <a href="https://github.com/eb-silverstar/RandomClient">RandomClient</a>에게 1초 주기로 전송합니다.
<br><br><img src="https://user-images.githubusercontent.com/97152402/160883408-9afa2d76-11dd-4e43-a5f0-00da057cec05.png" width="700" height="400">

### Environment
- JDK 11
- Oracle 18c
- InteliJ

### DDL
<a href="https://github.com/eb-silverstar/RandomCreator">RandomCreator</a>와 동일
```
CREATE TABLE random_master(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    random_number NUMBER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
);
```

### Build
[IntelliJ] **Build** > **Build Artifacts...** > **Build**

### Test
1. ~\RandomMaster\out\artifacts\RandomMaster_jar 로 이동
2. **java -jar RandomMaster.jar** 실행
3. <a href="https://github.com/eb-silverstar/RandomClient">RandomClient</a> 실행
<br><br><img src="https://user-images.githubusercontent.com/97152402/160885605-b5490b9f-070a-4ce7-a6c4-e3fdcc8ff508.png" width="300" height="600">

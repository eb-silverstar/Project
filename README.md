## RandomCreator
0.1초 주기로 Random한 정수를 생성하여 Local DB에 Insert 합니다.
<br><br><img src="https://user-images.githubusercontent.com/97152402/160879113-8e2f99b7-73b1-4c0e-95bf-a5e9b086a3c3.png" width="400" height="400">

### Environment
- JDK 11
- Oracle 18c
- InteliJ

### DDL
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
1. ~\RandomCreator\out\artifacts\RandomCreator_jar 로 이동
2. **java -jar RandomCreator.jar** 실행
<br><br><img src="https://user-images.githubusercontent.com/97152402/160880613-c6b698f8-a07d-4fe4-a0a9-e8e9f54b11ac.png" width="380" height="400">  <img src="https://user-images.githubusercontent.com/97152402/160881946-be8041fc-8bb1-4175-be1e-3e98eabe924b.png" width="370" height="400">

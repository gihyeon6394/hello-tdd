# Part 1. The Money Example

1. Multi-Currency Money
2. Degenerate Objects
3. Equality for All
4. Privacy
5. Franc-ly Speaking
6. Equality for All, Redux
7. Apples and Oranges
8. Makin' Objects
9. Times We're Livin' In
10. Interesting Times
11. The Root of All Evil
12. Addition, Finally
13. Make It
14. Change
15. Mixed Currencies
16. Abstraction, Finally
17. Money Retrospective

---

#### 흐름

- test 추가
- 모든 test 실행 -> failed 확인
- 수정
- 모든 test 실행 -> succeed 확인
- 리팩터링 (중복 제거)

#### 배울 내용

- test를 작성하며 funtionality 증가
- test 작성을 위해 필요한 변화
- test 실행 빈도
- 리팩터링에 필요한 것들

## 1. Multi-Currency Money

| Instrument | Shares | Price | Total |
|------------|--------|-------|-------|
| IBM        | 1000   | 25    | 25000 |
| GE         | 400    | 100   | 40000 |
|            |        |       | 65000 |

| Instrument | Shares | Price   | Total     |
|------------|--------|---------|-----------|
| IBM        | 1000   | 25 USD  | 25000 USD |
| GE         | 400    | 150 CHF | 60000 CHF |
|            |        |         | 65000 USD |

| From | To  | Rate |
|------|-----|------|
| CHF  | USD | 1.5  |

````
$5 + 10 CHF = $10 if rate is 2:1
$5 * 2 = $10
````

1. test 추가하기
2. test 실행 -> failed
3. 코드 수정 -> test 실행 -> succeed
4. 리팩터링 (중복 제거)

### step 1 : test 추가

````
public void testMultiplication() {
    Dollar five = new Dollar(5);
    five.times(2);
    assertEquals(10, five.amount);
}
````

```
$5 + 10 CHF = $10 if rate is 2:1
$5 * 2 = $10
Make "amount" private
Dollar side-effects?
Money rounding?
```

### step 2. compile error 해결 -> test failed

- Dollar class 생성
- Dollar class 생성자 생성
- Dollar class times() 생성
- Dollar class amount 필드 생성

```java
class Dollar {
    int amount;

    Dollar(int amount) {
    }

    void times(int multiplier) {
    }
}
````

### step3. test failed 해결

```java
class Dollar {
    int amount = 10; // stub

    // ...
}
````

### step4. 리팩터링

```java
public class Dollar {
    public int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public void times(int multiplier) {
        amount *= multiplier;
    }
}
```

### 정리

- 실행할 test 리스트업
- 테스트의 operation 정의
- JUnit detail 무시
- compile error 해결 (stub 활용)
- test 실행 -> succeed
- 리팩터링 : 상수 -> 변수 대체

## 2. Degenerate Objects

## 3. Equality for All

## 4. Privacy

## 5. Franc-ly Speaking

## 6. Equality for All, Redux

## 7. Apples and Oranges

## 8. Makin' Objects

## 9. Times We're Livin' In

## 10. Interesting Times

## 11. The Root of All Evil

## 12. Addition, Finally

## 13. Make It

## 14. Change

## 15. Mixed Currencies

## 16. Abstraction, Finally

## 17. Money Retrospective


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

### TDD cycle (목적 : 동작하는 clean code를 만들기)

1. test 작성 (story 작가처럼)
    - operation을 code에 생각 나는대로 작성
2. 동작하도록 만듦 (stub 등을 활용)
    - test 결과가 succeed (green bar)가 되도록 빠르게 만듦
    - 주로 타이핑으로 해결 (몇분 안에 끝날일이면 코드 작성)
    - 동작은 하지만, 코드가 깔끔하지 않음 (일단 더러운 부분을 메모해두고)
3. 올바르게 만듦 (stub 등을 해결)
    - 코드를 깔끔하게 만듦

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. Make "amount" private
> 4. Dollar side-effects?
> 5. Money rounding?

```
@Test
public void testMultiplication() {
   Dollar five= new Dollar(5);
   five.times(2);
   assertEquals(10, five.amount);
   five.times(3);
   assertEquals(15, five.amount);
}
```

```
@Test
public void testMultiplication() {
   Dollar five= new Dollar(5);
   Dollar product= five.times(2);
   assertEquals(10, product.amount);
   product= five.times(3);
   assertEquals(15, product.amount);
}

... 

Dollar times(int multiplier) {
   return new Dollar(amount * multiplier);
}
```

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. Make "amount" private
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?

### 빠르게 green bar를 만드는 전략

- 전략 1. Fake It : 상수를 반환하게 만듦 -> 상수를 변수로 대체
- 전략 2. Obvious Implementation : 가장 쉬운 방법으로 구현
- 전략 3. Triangulation
    - stub을 사용해 빠르게 compile error를 해결
    - right code를 추가해 test를 통과시킴

## 3. Equality for All

### Value Object : Object를 value처럼 사용하기

- Integer에 1을 추가한다고, 기존 Integer의 값이 바뀌지 않는다.
- VO의 value는 불변이다. (생성자로부터 주입받은 뒤 변경되지 않는다)

#### VO 장점 : ailiasing 문제를 회피

- ailiasing : 두 개의 reference가 같은 object를 가리키는 것
- ailiasing 문제를 회피하기 위해 VO는 불변이다.

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. Make "amount" private
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. equals()

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
}

...

@Override
public boolean equals(Object object) {
    return true;
}
````

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
}


@Override
public boolean equals(Object obj) {
    Dollar dollar = (Dollar) obj;
    return amount == dollar.amount;
}
````

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. Make "amount" private
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)

````
@Override
public boolean equals(Object object) {
    Dollar dollar= (Dollar) object;
    return amount == dollar.amount;
}

...

@Override
public int hashCode() {
    return amount;
}

````

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertFalse(new Dollar(5).equals(null));
}

...

@Override
public boolean equals(Object object) {

    if(object == null) {
        return false;
    }

    Dollar dollar= (Dollar) object;
    return amount == dollar.amount;
}

````

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertFalse(new Dollar(5).equals(null));
    assertFalse(new Dollar(5).equals(new Object()));
}

...

@Override
public boolean equals(Object object) {

    if(object == null) {
        return false;
    }

    if(!(object instanceof Dollar)) {
        return false;
    }

    Dollar dollar= (Dollar) object;
    return amount == dollar.amount;
}
````

## 4. Privacy

- functionality를 활용해 test 향상 (VO, equals)
- 한번의 2개의 test가 모두 실패할 수 있는 리스크
- 에도 불구하고 진행
- test와 code의 느슨한 결합을 위해 functionality를 활용

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. Make "amount" private
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)

```
// as-is
public void testMultiplication() {
    Dollar five = new Dollar(5);
    Dollar product = five.times(2);
    assertEquals(10, product.amount);
    product = five.times(3);
    assertEquals(15, product.amount);
}

// to-be
@Test
public void testMultiplication() {
    Dollar five= new Dollar(5);
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
}

...

private int amount;
````

- `Dollar` VO로 비교
- 임시 변수 `product` 제거
- `amount` 필드를 private로 변경

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)

## 5. Franc-ly Speaking

- 큰 테스트에 대응할 수 없으니 더 작은 테스트 생성
- 뻔뻔하게 중복 테스트 작성
- VO를 그대로 복사해 `Franc` 생성
- 중복이 사라질때까지 집에 가지마

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. 5 CHF * 2 = 10 CHF

````
@Test
public void testFrancMultiplication() {
    Franc five = new Franc(5); // compile error : cannot find symbol class Franc
    assertEquals(new Franc(10), five.times(2)); // compile error : cannot find symbol class Franc
    assertEquals(new Franc(15), five.times(3)); // compile error : cannot find symbol class Franc
}
````

### 속도를 위해 디자인 패턴을 무시

1. Write a test
2. Make it compile
3. Run it to see it fail : **여기까지 매우 빠르게**, copy `Dollar` & paste
4. Make it run
5. Remove duplication

```java
class Franc {
    private int amount;

    Franc(int amount) {
        this.amount = amount;
    }

    Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    public boolean equals(Object object) {
        Franc franc = (Franc) object;
        return amount == franc.amount;
    }
}
```

### 결과

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. Common equals
> 13. Common times

## 6. Equality for All, Redux

- common code를 class에서 superclass로 옮김
- `Dollar` & `Franc`의 중복 제거 (`equals()`)

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. Common equals
> 13. Common times

```java
class Dollar extends Money {
    protected int amount;

    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }

        if (!(object instanceof Dollar)) {
            return false;
        }

        Money money = (Money) object;
        return amount == money.amount;
    }


    @Override
    public int hashCode() {
        return amount;
    }
}

class Dollar extends Money {
    // private int amount;

    // no need to override equals() & hashCode()

}
```

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertFalse(new Dollar(5).equals(null));
    assertFalse(new Dollar(5).equals(new Object()));

    assertTrue(new Franc(5).equals(5));
    assertFalse(new Franc(5).equals(6));
}
````

### 결과

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. ~~Common equals~~
> 13. Common times

## 7. Apples and Oranges

- `getClass()` 사용해서 test 통과

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. ~~Common equals~~
> 13. Common times
> 14. Compare Francs with Dollars

````
@Test
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertFalse(new Dollar(5).equals(null));
    assertFalse(new Dollar(5).equals(new Object()));

    assertTrue(new Franc(5).equals(new Franc(5)));
    assertFalse(new Franc(5).equals(new Franc(6)));

    assertFalse(new Franc(5).equals(new Dollar(5))); // shit! return true
}
````

```java
class Dollar extends Money {
    protected int amount;

    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }

        if (!(object instanceof Dollar)) {
            return false;
        }

        Money money = (Money) object;
        return amount == money.amount
                && getClass().equals(money.getClass()); // Class 객체 검증 추가

    }

}
```

### 결과

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. ~~Common equals~~
> 13. Common times
> 14. ~~Compare Francs with Dollars~~
> 15. Currency?

## 8. Makin' Objects

- 중복 `times()` 제거
- `times()` 를 superclass로
- test 와 subclass decoupling : factory method

> ### story
>
> 1. $5 + 10 CHF = $10 if rate is 2:1
> 2. ~~$5 * 2 = $10~~
> 3. ~~Make "amount" private~~
> 4. ~~Dollar side-effects?~~
> 5. Money rounding?
> 6. ~~equals()~~
> 7. hashCode() (TODO)
> 8. Equal null (TODO)
> 9. Equal object (TODO)
> 10. ~~5 CHF * 2 = 10 CHF~~
> 11. Dollar/Franc duplication
> 12. ~~Common equals~~
> 13. Common times
> 14. ~~Compare Francs with Dollars~~
> 15. Currency?

```java
public class Dollar extends Money {
    // ...
    public Money times(int multiplier) {
        return new Dollar(amount * multiplier);
    }
}

public class Franc extends Money {
    // ...
    public Money times(int multiplier) {
        return new Franc(amount * multiplier);
    }
}
```

````
@Test
public void testMultiplication() {
    Money five = Money.dollar(5); // compile error : cannot find symbol class Money
    assertEquals(new Dollar(10), five.times(2)); // compile error : cannot find symbol method times(int)
    assertEquals(new Dollar(15), five.times(3));
}
````

```java

public abstract class Money {

    public static Money dollar(int amount) {
        return new Dollar(amount);
    }

    public static Money franc(int amount) {
        return new Franc(amount);
    }

    public abstract Money times(int multiplier);
    // ...
}
```

```java
public class MoneyTest {

    @Test
    public void testMultiplication() {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    public void testEquality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.dollar(5).equals(null));
        assertFalse(Money.dollar(5).equals(new Object()));

        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));

        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    public void testFrancMultiplication() {
        Money five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }
}

````

## 9. Times We're Livin' In

## 10. Interesting Times

## 11. The Root of All Evil

## 12. Addition, Finally

## 13. Make It

## 14. Change

## 15. Mixed Currencies

## 16. Abstraction, Finally

## 17. Money Retrospective


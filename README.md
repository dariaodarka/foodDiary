# Food Diary App

Приложение для отслеживания питания и нормы калорий. Реализована регистрация пользователя,
и дальнейшая аутенфикация с использованием JWT. Так же есть доступные функции добавления
блюд, добавления приемов пищи, а так же получения отчетов за выбранный срок или за один
день. Так же есть возможность генерации отчетов питания в csv и pdf форматах.


## Технологии
- Java 17
- Spring Boot 3.4.3
- PostgreSQL 17.2
- Maven

## Запуск
1. Установите PostgreSQL и создайте БД `food_diary`
2. Настройте `src/main/resources/application.yml`:

spring:\
datasource:\
url: jdbc:postgresql://localhost:5432/food_diary \
username: ваш_логин \
password: ваш_пароль

3. Клонируйте репозиторий https://github.com/dariaodarka/foodDiary.git
4. Запустите проект

## API Endpoints
| Метод | Путь          | Описание                 |
|-------|---------------|--------------------------|
| POST  | /registerUser | Регистрация пользователя |
| POST  | /login        | Аутенфикация пользователя |
| POST  | /createDish   | Добавить блюдо           |
| POST  | /createMeal   | Добавить прием пищи      |
| GET   | /getDailyReport | Получить список приемов пищи за определенный день |
| GET | /getMealHistory | Получить историю питания за определенный срок |
| GET | /dowloadReport | Сгенерировать отчет по питанию |
| GET | /getDishes | Получить список блюд |

* Постман коллекция находится в food-diary/docs/FoodDiary API.postman_collection.json

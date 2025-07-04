# 🎮 **ElementCraft** - плагин для сервера ElementCraft

**✨ Универсальное решение для администрирования, торговли и управления инвентарём!**  

---

## 🔧 **Техническое устройство**  
**🧠 Ядро:**  
- Написано на **Java** с использованием **Spigot/Bukkit API**  
- Модульная архитектура (4 независимых системы)  
- Оптимизированная работа с **YAML** и **SQLite**  

**📦 Основные компоненты:**  
1. **Менеджер объектов** (правила A-E)  
2. **Система торговли** (эндер-жемчуга ↔ золото)  
3. **Двойной хотбар** (Shift+F переключение)  
4. **Логирование в БД** (игроки + транзакции)  

---

## 🎯 **Что умеет плагин?**  

### 📜 **1. Управление объектами**  
| Команда | Описание | Пример |  
|---------|----------|--------|  
| `/object create` | Создать объект 🏗️ | `/object create Дом` |  
| `/object add` | Добавить правило (A-E) ➕ | `/object add Дом A,B,C,D,E` |  
| `/object info` | Просмотр правил 🔍 | `/object info Дом` |  
| `/object remove` | Удалить правило ❌  | `/object remove Дом A,B,C,D,E` | 
| `/object delete` | Удалить объект ❌ | `/object delete Дом` | 

**⚡ Особенности:**  
- Автодополнение команд (Tab)  
- Хранение в **config.yml**  

### 🧙 **2. Умные торговцы**  
- **Автоматически** добавляет сделку:  
  `1 эндер-жемчуг` 💎 ↔ `1-3 золотых слитка` 💰  
- Ведёт **полный лог** всех транзакций в БД 📊  

### 🎒 **3. Двойной хотбар**  
- **Горячие клавиши:** Shift + F 🔄  
- Сохраняет содержимое **между перезаходами** 💾  
- Работает **без лагов** (оптимизированный код)  

---

## 🚀 **Установка**  

### 📥 **Способ 1: Готовый JAR**  
1. Скачайте `ElementCraft.jar`
2. Переместите в `/plugins`  
3. Перезагрузите сервер ♻️  

### 🔨 **Способ 2: Сборка из исходников**  
```bash
git clone https://github.com/ваш-репозиторий.git
cd ElementCraft
mvn clean package
```
➡️ Готовый файл: `/target/ElementCraft.jar`  

---

## ⚙️ **Конфигурация**  
📂 **Файлы настроек:**  
- `config.yml` - Объекты и правила  
- `trades.db` - База данных транзакций  

**Пример конфига:**  
```yaml
objects:
  Дом:
    rules: [A, C]
  Сундук:
    rules: [B]
```

---

## 📊 **Статистика**  
✔ Поддержка **Minecraft 1.13+**  
✔ Оптимизирован под **20+ игроков онлайн**  
✔ Всего **<500 строк кода** (легковесный)  




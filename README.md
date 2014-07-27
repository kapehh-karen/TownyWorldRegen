TownyWorldRegen
===============

<b>Команды:</b>
<ul>
<li><code>/twr set worldName x1 y1 z1 x2 y2 z2 patternString</code> - заполняет выбранный регион блоками</li>
<li><code>/twr regen worldName x1 y1 z1 x2 y2 z2</code> - регенерироует выбранный регион</li>
</ul>

<b>Примеры:</b>
<ul>
<li><code>/twr set world 0 10 0 100 255 100 50%0,50%35:4</code> - заполнит в мире world с координат 0,10,0 до 100,255,100 область на половину воздухом и на вторую половину желтой шерстью</li>
<li><code>/twr regen world 0 10 0 100 255 100</code> - сгенерирует по новой область</li>
</ul>

<b>Конфиг:</b>
<pre>townyworldregen:
    enabled: true # Вкл / Выкл обработку Towny чанков, <b>если включено, то требует плагин Towny</b>
    items-count: 2
    item-1:
        id: 4 # идентификатор блока
        data: 0 # данные блока (если -1 то любые блоки с идентификатором ID)
        chance: 50 # шанс удаления блоков из чанка
        remove: 10 # общий процент удаления блоков из чанка
    item-2:
        id: 13
        data: 0
        chance: 50
        remove: 5</pre>

<b>Права:</b> доступно только из консоли

<b>Требует:</b> WorldEdit, PluginManager, [Towny]

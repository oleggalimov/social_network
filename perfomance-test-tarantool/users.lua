-- 1.создадим схему
users = box.schema.space.create('users');
users:format({
    {name = 'id', type = 'unsigned'},
    {name = 'user_id', type = 'string'},
    {name = 'password', type = 'string'},
    {name = 'last_name', type = 'string'},
    {name = 'first_name', type = 'string'},
    {name = 'age', type = 'unsigned'},
    {name = 'sex', type = 'string'},
    {name = 'interests', type = 'string'},
    {name = 'city', type = 'string'}
});
-- 2. Создадим индексы
users:create_index('primary', {
    type = 'tree',
    parts = {'id'},
});
users:create_index('fio_idx', {
    parts = {
        {'last_name'},
        {'first_name'}
    },
    unique = false
});

-- 3. Создадим хранимую процедуру
function find_users(first_name, last_name)
    return box.space.users.index.fio_idx:select({last_name, first_name})
end;

-- 4 наполним данными
uuid = require('uuid');
dofile('/opt/tarantool/populate_users.lua');

-- 5 првоеряем что записи присутствуют
--users:select{1};
users = box.schema.space.create('users');
users:format({
    {name = 'id', type = 'unsigned'},
    {name = 'user_id', type = 'string'},
    {name = 'password', type = 'string'},
    {name = 'first_name', type = 'string'},
    {name = 'second_name', type = 'string'},
    {name = 'age', type = 'unsigned'},
    {name = 'sex', type = 'string'},
    {name = 'interests', type = 'string'},
    {name = 'city', type = 'string'}
    });
users:create_index('primary', {
    type = 'tree',
    parts = {'id'}
    });
box.space.users:insert{
    1,
    '9556a01c-ab60-4e63-b985-c728bbe31e02',
    '$2a$10$enkumyeGPBJ4/UBqXuuJtO/E3ZNLvKpeTNE8ySAr.5RleeTfB2p02', 
    'Леди', 
    'Гага', 
    32, 
    'female',
    '',
    'Воткинск'};
users:select{1}; --box.space.users:select(1);

local socket = require("socket")
local uuid = require("uuid")
uuid.seed()
local id = uuid()

for i = 3,9,1 
do 
    users:insert{
        2,
        uuid.new(),
        '$2a$10$enkumyeGPBJ4/UBqXuuJtO/E3ZNLvKpeTNE8ySAr.5RleeTfB2p02', 
        'Леди', 
        'Гага', 
        32, 
        'female',
        '',
        'Воткинск'};
end


--CREATE TABLE public.users (id BIGINT DEFAULT nextval('public.user_id_seq') NOT NULL, user_id VARCHAR NOT NULL, first_name VARCHAR NOT NULL, second_name VARCHAR NOT NULL, birth_date date NOT NULL, biography VARCHAR, city VARCHAR, password VARCHAR NOT NULL)
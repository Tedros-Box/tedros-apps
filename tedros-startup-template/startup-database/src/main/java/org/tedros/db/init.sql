create user if not exists tdrs  PASSWORD 'xpto'  ADMIN;
create schema if not exists tedros_core authorization tdrs;
create schema if not exists tedros_apps authorization tdrs;
create schema if not exists tedros_ext authorization tdrs;
create schema if not exists tedros_common authorization tdrs;
create schema if not exists tedros_samples authorization tdrs;

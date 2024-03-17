local bucket_key = KEYS[1] -- 令牌桶的key
local rate = tonumber(ARGV[1]) -- 令牌填充速率
local capacity = tonumber(ARGV[2]) -- 令牌桶的容量
local needed_tokens = tonumber(ARGV[3]) -- 需要消耗的令牌数
local time = redis.call('TIME') -- 当前时间戳
local now = time[1] * 1000 + math.floor(time[2] / 1000)
local last_fill_time = tonumber(redis.call('hget', bucket_key, 'last_fill_time')) or 0 -- 上次填充令牌的时间
local tokens_remained = tonumber(redis.call('hget', bucket_key, 'tokens')) or capacity -- 当前令牌数量

if tokens_remained - needed_tokens >= 0 then
    -- 资源数足够时，扣减资源后放行
    redis.call('hset',bucket_key,'tokens', tokens_remained - needed_tokens)
    return 1
end

-- 资源不足时，先补充，再消耗
-- 计算自上次填充以来应该添加的令牌数量
local to_add = math.floor((now - last_fill_time) / 1000 * rate)
--redis.call('lpush', "to_add", to_add)
-- 资源没得到补充，依旧不足抵扣消耗
if to_add <=0 then
    return 0
end

tokens_remained = math.min(capacity, tokens_remained + to_add) - needed_tokens
if tokens_remained >= 0 then
    -- 更新填充时间和令牌数量
    redis.call('hset', bucket_key, 'last_fill_time', now)
    redis.call('hset', bucket_key, 'tokens', tokens_remained)
    return 1
end
-- 令牌不足
return 0
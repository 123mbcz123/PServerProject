PServerProject
=
这是我随意写的一个聊天软件后端
Socket对接格式如下:

-数据传输格式: [数据1][数据2][数据3]...  #使用[]隔开数据为了防止沾包
-[]内的数据格式:
-- 序列化HashMap的Json
-- 需要对不可控字符串进行base64加密
#防止程序抽风
数据定义格式:
HashMap内数据如下:
Key= xxx vaule=xxx #格式
#聊天格式
Key=Cmd vaule=Chat #声明本次为聊天事件
Key=TypeId vaule="1" or "2" #1:群聊 2:私聊
Key=Msg vaule=XXX #XXX为发送的消息,此消息需要经过base64加密后发送!
Key=ToId vaule=XXX #XXX为群号/私聊的对方账号 此消息为String格式的数字!
#请把以上数据封装到Map中转换成json并在json的首位分别加上"[" "]"


#登入格式
Key=Cmd vaule=Auth #声明本次为登入事件
Key=User vaule=XXX #XXX为String格式的数字
Key=Pass vaule=XXX #XXX为密码 此密码需要经过base64加密后发送!
#加密方法 MD5(MD5(密码)+$salt) $salt 从数据库中获得

#窝虽然加入了Gzip压缩\Zip压缩类
#但是经过测试效率并不高 因此窝把他作为可选功能加入服务端
===================完===================

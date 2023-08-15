## neko_movie接口文档

### 1 member用户微服务

- api均以微服务名开头从 gateway 访问微服务，去掉微服务名直接访问微服务



#### 1.1 MemberInfoController

##### 1.1.1 用户登录

- post请求，请求体传参

```bash
$baseUrl/member/member_info/log_in
```



- 参数

| 参数名       | 参数含义                      |
| ------------ | ----------------------------- |
| userName     | 用户名                        |
| userPassword | 密码，需要RSA加密后Base64编码 |



- 示例

```bash
{
    "userName": "NEKO",
    "userPassword": "QWTfNFa5JBWNDjawnGp2HShWBIWSXagEmQ/bttkmApglyPbdGRDUAwUzGgI/DGIZyhimznKlB0FTQtnOFWm93A0uzEKFKtgiOHxiOAouPR1AU8erCiSFwH0LvqcsdhTVzx3BRa5OvEfq2jsSQNjNxnAhcEL8YV4VjOVbBWnH0Bo="
}
```



- 响应格式

```json
{
    "result": {
        "uid": "1642067605873348610",
        "userName": "NEKO",
        "sourceName": null,
        "userImagePath": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-25/1430d607-b0a4-4f83-aa5c-bedb89a0d888_301882efc3b3dcabaad03a7b607ab228.jpg",
        "weightTypes": [
            "base",
            "market_read",
            "market_write",
            "courier_read",
            "courier_write",
            "基本视频观看"
        ],
        "roleTypes": [
            "user",
            "market",
            "courier",
            "普通会员"
        ],
        "memberLevelRoleTypes": [
            "1级会员",
            "2级会员"
        ],
        "token": "cec15363-7d2b-43e4-9239-2677b15730a2",
        "gender": null,
        "source": null,
        "sourceUid": null,
        "realName": "NEKO",
        "idCardNumber": "420881200101184053",
        "phone": null,
        "mail": "NEKO@NEKO.com",
        "isBan": false,
        "isDelete": false,
        "createTime": "2023-04-01T15:33:20",
        "updateTime": "2023-07-29T17:40:33"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.2 用户注册

- post请求，url传参

```bash
$baseURl/member/member_info/register
```



- 参数

| 参数名       | 参数含义 |
| ------------ | -------- |
| userName     | 用户名   |
| userPassword | 密码     |
| email        | 邮箱     |
| code         | 验证码   |



- 示例

```bash
$baseUrl/member/member_info/register?userName=koori&userPassword=Liekonggenji2&email=2665249580@qq.com&code=548822
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.3 确认用户名是否重复

- get请求，url传参

```bash
$baseUrl/member/member_info/user_name_is_repeat
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| userName | 用户名   |



- 示例

```bash
$baseUrl/member/member_info/user_name_is_repeat?userName=$userName
```



- 响应结果

```json
{
    "result": true,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.4 发送注册邮件

- post请求

```bash
$baseUrl/member/member_info/send_register_mail
```



- 参数

| 参数名 | 参数含义   |
| ------ | ---------- |
| mail   | 接收者邮件 |



- 示例

```bash
$baseUrl/member/member_info/send_register_mail?mail=$mail
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.5 添加用户积分

- post请求，请求体传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/member_info/add_point
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| uid    | 用户uid  |
| point  | 添加积分 |



- 示例

```json
{
    "uid": "1642067605873348610",
    "point": 500
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.6 根据uid获取真实姓名

- post请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/member_info/real_name_info
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| uid    | 用户uid  |



- 示例

```bash
$baseUrl/member/member_info/real_name_info?uid=1642067605873348610
```



- 响应结果

```json
{
    "result": "NEKO",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.7 修改用户名

- post请求，url传参
- 需要登录状态

```bash
$baseUrl/member/member_info/update_user_name
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| userName | 用户名   |



- 示例

```bash
$baseUrl/member/member_info/update_user_name?userName=koori
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.8 修改密码

- post请求，请求体传参
- 需要登录状态

```bash
$baseUrl/member/member_info/update_user_password
```



- 参数

| 参数名       | 参数含义                        |
| ------------ | ------------------------------- |
| userPassword | 原密码，需要RSA加密后Base64编码 |
| todoPassword | 新密码，需要RSA加密后Base64编码 |



- 示例

```json
{
    "userPassword": "iD6HqGdF9KFd9IdwMETzPWNzqI4BwOwvOeoWsnhki++gzponD0vjJlRqIOwVN/B3HStTlicvf3SaDjSFRrG8ePAuqNtjBptSgghwZrHR0WcGY8Rf5FJSReiq1Th5lJmc66bfwlH8mB3Kn7MqM1t2fXIe41x9cTqVXtG6j4L6cc0=",
    "todoPassword": "G7RlQuZluYEVraZ7fFKnFhjhS/5/85L45Bvr0Q/Ceh/qMxAdtq4gNF8yCg1LzPgxB302YpQpVBkcXuwxy3HqUNdZjdhHRyxDbCFm69K8Co6Yf2trYhH5z2COJ8WTzYFfPVgDzhgCf9RL5mg1hQArI3yOBIuJ23P5aPl4TYXZVZA="
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.9 修改头像

- post请求，form-data上传文件
- 需要登录状态

```bash
$baseUrl/member/member_info/update_user_image_path
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| file   | 文件     |



- 响应结果

```json
{
    "result": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_convenient/2023-06-25/cdacafeb-8a7a-4c79-b3b3-145520c9d097.png",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.1.10 获取用户基本信息

- get请求
- 需要登录状态

```bash
$baseUrl/member/member_info/user_info
```



- 响应结果

```json
{
    "result": {
        "uid": "1642067605873348610",
        "userName": "NEKO",
        "sourceName": null,
        "userImagePath": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-25/1430d607-b0a4-4f83-aa5c-bedb89a0d888_301882efc3b3dcabaad03a7b607ab228.jpg",
        "weightTypes": [
            "base",
            "market_read",
            "market_write",
            "courier_read",
            "courier_write",
            "基本视频观看"
        ],
        "roleTypes": [
            "user",
            "market",
            "courier",
            "普通会员"
        ],
        "memberLevelRoleTypes": [
            "1级会员",
            "2级会员"
        ],
        "token": null,
        "gender": null,
        "source": null,
        "sourceUid": null,
        "realName": "NEKO",
        "idCardNumber": "420881200101184053",
        "phone": null,
        "mail": "NEKO@NEKO.com",
        "isBan": false,
        "isDelete": false,
        "createTime": "2023-04-01T15:33:20",
        "updateTime": "2023-07-29T17:40:33"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.2 AdminInfoController

##### 1.2.1 管理员登录

- post请求，url传参

```bash
$baseUrl/member/admin_info/log_in
```



- 参数

| 参数名       | 参数含义          |
| ------------ | ----------------- |
| userName     | 用户名            |
| userPassword | 密码，需要RSA加密 |



- 示例

```bash
{
    "userName": "NEKO",
    "userPassword": "K1fPQ3TFolOdP56BES51CL25KW/VcZVcYPR9GyWG5Zr657Ke767sVU+QzlxDATmJh7fvq30Png+ptJMAuJonlfKn1TmeH6GS5iEzmXjltBgKtmm6tCtWMFItNNlPtUczXNpoE1TiYkI99WwIKYvuYLSKSJK/vCGHNWx8/iUhTL4="
}
```



- 响应格式

```json
{
    "result": {
        "adminId": "1642398369596944385",
        "userName": "NEKO",
        "userImagePath": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_market/2023-01-22/1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
        "token": "2ee7d0a6-8317-43ea-8641-2dd5375db8be",
        "weightTypes": [
            "*"
        ],
        "roleTypes": [
            "*"
        ],
        "operateAdminId": null,
        "isDelete": false,
        "createTime": "2023-04-01T21:17:37",
        "updateTime": "2023-04-01T21:17:41"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.2.2 新增管理员

- put请求，请求体传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/admin_info/new_admin
```



- 参数

| 参数名       | 参数含义     |
| ------------ | ------------ |
| userName     | 用户名       |
| userPassword | 密码         |
| roleIds      | 角色id，数组 |



- 示例

```json
{
    "userName": "$userName",
    "userPassword": "$userPassword",
    "roleIds": [5, 15]
}
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.2.3 确认用户名是否重复

- get请求，url传参
- 需要root角色
- 需要登录状态

```bash
$baseUrl/member/admin_info/user_name_is_repeat?userName=NEKO
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| userName | 用户名   |



- 示例

```bash
$baseUrl/member/admin_info/user_name_is_repeat?userName=NEKO
```



- 响应格式

```json
{
    "result": true,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.3 UserWeightController

##### 1.3.1 管理员新增普通权限

- put请求，url传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/new_user_weight
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| weightType | 权限名   |



- 示例

```bash
$baseUrl/member/user_weight/new_user_weight?weightType=$weightType
```



- 响应状态

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.2 管理员分页查询普通权限信息

- post请求，请求体传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/weight_info
```



- 参数

| 参数名      | 参数含义         |
| ----------- | ---------------- |
| queryWords  | 查询关键字，可选 |
| currentPage | 当前页数         |
| limited     | 每页数量         |



- 示例

```json
{
    "currentPage": 1,
    "limited": 8
}
```



- 响应格式

```json
{
    "result": {
        "records": [
            {
                "weightId": 1,
                "weightType": "base",
                "createTime": "2023-03-27T16:03:51",
                "updateTime": "2023-03-27T16:03:54"
            },
            {
                "weightId": 2,
                "weightType": "*",
                "createTime": "2023-04-01T21:18:11",
                "updateTime": "2023-04-01T21:18:14"
            },
            {
                "weightId": 3,
                "weightType": "high_read",
                "createTime": "2023-04-01T21:24:27",
                "updateTime": "2023-04-01T21:24:29"
            },
            {
                "weightId": 4,
                "weightType": "high_write",
                "createTime": "2023-04-01T21:24:43",
                "updateTime": "2023-04-01T21:24:46"
            },
            {
                "weightId": 5,
                "weightType": "root_read",
                "createTime": "2023-04-02T13:37:23",
                "updateTime": "2023-04-02T13:37:26"
            },
            {
                "weightId": 6,
                "weightType": "root_write",
                "createTime": "2023-04-02T13:37:34",
                "updateTime": "2023-04-02T13:37:36"
            }
        ],
        "total": 6,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.3 管理员查询指定roleId还未绑定普通权限信息

- post请求，url传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/unbind_weight_info
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| roleId | 角色id   |



- 示例

```bash
$baseUrl/member/user_weight/unbind_weight_info?roleId=1
```



- 响应格式

```json
{
    "result": [
        {
            "weightId": 2,
            "weightType": "*",
            "createTime": "2023-04-01T21:18:11",
            "updateTime": "2023-04-01T21:18:14"
        },
        {
            "weightId": 3,
            "weightType": "high_read",
            "createTime": "2023-04-01T21:24:27",
            "updateTime": "2023-04-01T21:24:29"
        },
        {
            "weightId": 4,
            "weightType": "high_write",
            "createTime": "2023-04-01T21:24:43",
            "updateTime": "2023-04-01T21:24:46"
        },
        {
            "weightId": 5,
            "weightType": "root_read",
            "createTime": "2023-04-02T13:37:23",
            "updateTime": "2023-04-02T13:37:26"
        },
        {
            "weightId": 6,
            "weightType": "root_write",
            "createTime": "2023-04-02T13:37:34",
            "updateTime": "2023-04-02T13:37:36"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.4 管理员新增会员等级类型权限

- put请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/new_member_level_weight
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| weightType | 权限名   |



- 示例

```bash
$baseUrl/member/user_weight/new_member_level_weight?weightType=1类视频
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.5 管理员获取指定roleId还未绑定会员等级权限信息

- post请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/unbind_member_level_weight_info
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| roleId | 角色名   |



- 示例

```bash
$baseUrl/member/user_weight/unbind_member_level_weight_info?roleId=11
```



- 响应结果

```json
{
    "result": [
        {
            "weightId": 16,
            "weightType": "1类视频",
            "type": 1,
            "createTime": "2023-07-26T10:45:56",
            "updateTime": "2023-07-26T10:45:58"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.6 管理员分页查询会员等级类型权限信息

- post请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_weight/member_level_weight_info
```



- 参数

| 参数名      | 参数含义         |
| ----------- | ---------------- |
| queryWords  | 查询关键字，可选 |
| currentPage | 当前页数         |
| limited     | 每页数量         |



- 示例

```json
{
    "queryWords": "courier_read",
    "currentPage": 1,
    "limited": 8
}
```



- 响应结果

```json
{
    "result": {
        "records": [
            {
                "weightId": 11,
                "weightType": "courier_read",
                "type": 1,
                "createTime": "2023-05-25T12:04:27",
                "updateTime": "2023-05-25T12:04:27"
            }
        ],
        "total": 1,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.7 获取会员等级类型全部权限信息

- get请求

```bash
$baseUrl/member/user_weight/member_level_weight_infos
```



- 响应结果

```json
{
    "result": [
        {
            "weightId": 16,
            "weightType": "基本视频观看",
            "type": 1,
            "isDelete": false,
            "createTime": "2023-07-26T16:00:49",
            "updateTime": "2023-07-26T16:00:49"
        },
        {
            "weightId": 17,
            "weightType": "1级视频观看",
            "type": 1,
            "isDelete": false,
            "createTime": "2023-07-26T16:08:59",
            "updateTime": "2023-07-26T16:08:59"
        },
        {
            "weightId": 18,
            "weightType": "2级视频观看",
            "type": 1,
            "isDelete": false,
            "createTime": "2023-07-26T16:09:12",
            "updateTime": "2023-07-26T16:09:12"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.8 根据weightId获取会员等级类型权限名

- get请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/user_weight/member_level_weight_name_by_weight_id
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| weightId | 权限id   |



- 示例

```bash
$baseUrl/member/user_weight/member_level_weight_name_by_weight_id?weightId=16
```



- 响应结果

```json
{
    "result": "基本视频观看",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.9 管理员删除指定weightId普通权限类型权限名

- delete请求，url传参
- 需要登录状态
- 需要root角色

```bash
$baseUrl/member/user_weight/delete_weight
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| weightId | 权限id   |



- 示例

```bash
$baseUrl/member/user_weight/delete_weight?weightId=19
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.3.10 管理员删除指定weightId会员等级类型权限名

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/member/user_weight/delete_member_level_weight
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| weightId | 权限id   |



- 示例

```bash
$baseUrl/member/user_weight/delete_member_level_weight?weightId=21
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.4 UserRoleController

##### 1.4.1 管理员新增非会员等级类型角色信息角色

- put请求，请求体传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/user_role/new_user_role
```



- 参数

| 参数名   | 参数含义                                         |
| -------- | ------------------------------------------------ |
| roleType | 角色名                                           |
| type     | 角色类型种类，0->普通角色类型，1->管理员角色类型 |



- 示例

```json
{
    "roleType": "base_admin",
    "type": 0
}
```



- 响应状态

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.4.2 管理员分页查询非会员等级类型角色信息

- post请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_role/role_info
```



- 参数

| 参数名      | 参数含义         |
| ----------- | ---------------- |
| queryWords  | 查询关键字，可选 |
| currentPage | 当前页数         |
| limited     | 每页数量         |



- 示例

```json
{
    "currentPage": 1,
    "limited": 8
}
```



- 响应状态

```json
{
    "result": {
        "records": [
            {
                "roleId": 1,
                "roleType": "user",
                "createTime": "2023-03-27T16:03:21",
                "updateTime": "2023-03-27T16:03:24"
            },
            {
                "roleId": 2,
                "roleType": "*",
                "createTime": "2023-04-01T21:17:57",
                "updateTime": "2023-04-01T21:18:00"
            },
            {
                "roleId": 3,
                "roleType": "admin",
                "createTime": "2023-04-01T21:21:11",
                "updateTime": "2023-04-01T21:21:13"
            },
            {
                "roleId": 4,
                "roleType": "root",
                "createTime": "2023-04-02T13:36:49",
                "updateTime": "2023-04-02T13:36:52"
            }
        ],
        "total": 4,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.4.3 管理员查询管理员角色信息

- post请求，无参数
- 需要root角色
- 需要登录状态

```bash
$baseUrl/member/user_role/admin_role_info
```



- 响应格式

```json
{
    "result": [
        {
            "roleId": 2,
            "roleType": "*",
            "isAdmin": true,
            "createTime": "2023-04-01T21:17:57",
            "updateTime": "2023-04-01T21:18:00"
        },
        {
            "roleId": 3,
            "roleType": "admin",
            "isAdmin": true,
            "createTime": "2023-04-01T21:21:11",
            "updateTime": "2023-04-01T21:21:13"
        },
        {
            "roleId": 4,
            "roleType": "root",
            "isAdmin": true,
            "createTime": "2023-04-02T13:36:49",
            "updateTime": "2023-04-02T13:36:52"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.4.4 新增会员等级类型角色信息角色

- put请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_role/new_member_level_role
```



- 参数

| 参数名   | 参数含义 |
| -------- | -------- |
| roleType | 角色名   |



- 示例

```bash
$baseUrl/member/user_role/new_member_level_role?roleType=普通会员
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.4.5 管理员删除普通角色，管理员角色类型角色信息

- delete请求，url传参
- 需要root角色
- 需要登录状态

```bash
$baseUrl/member/user_role/delete_role
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| roleId | 角色id   |



- 示例

```bash
$baseUrl/member/user_role/delete_role?roleId=17
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.4.6 删除会员等级角色类型角色信息

- delete请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/user_role/delete_member_level_role
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| roleId | 角色id   |



- 示例

```bash
$baseUrl/member/user_role/delete_member_level_role?roleId=19
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.5 WeightRoleRelationController

##### 1.5.1 管理员新增普通类型权重，角色关系

- put请求，请求体传参
- 需要 root 角色
- 需要登录状态

```bash
$baseUrl/member/weight_role_relation/new_relations
```



- 参数

| 参数名    | 参数含义     |
| --------- | ------------ |
| roleId    | 角色id       |
| weightIds | 权限id，数组 |



- 示例

```json
{
    "roleId": 1,
    "weightIds": [5, 15]
}
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.5.2 管理员获指定roleId权限，角色关系

- post请求，url传参
- 需要admin权限
- 需要登录状态

```bash
$baseUrl/member/weight_role_relation/relation_info_by_role_id
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| roleId | 角色id   |



- 示例

```bash
$baseUrl/member/weight_role_relation/relation_info_by_role_id?roleId=1
```



- 响应格式

```json
{
    "result": [
        {
            "relationId": 1,
            "weightId": 1,
            "weightType": "base",
            "roleId": 1,
            "roleType": "user",
            "createTime": "2023-03-27T16:04:06",
            "updateTime": "2023-03-27T16:04:08"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.5.3 获指定uid权限，角色关系

- post请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/weight_role_relation/relation_info_by_uid
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| uid    | uid      |



- 示例

```bash
$baseUrl/member/weight_role_relation/relation_info_by_uid?uid=1642067605873348610
```



- 响应格式

```json
{
    "result": [
        {
            "relationId": 1,
            "weightId": 1,
            "weightType": "base",
            "roleId": 1,
            "roleType": "user",
            "createTime": "2023-03-27T16:04:06",
            "updateTime": "2023-03-27T16:04:08"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.5.4 管理员新增会员等级类型权限，角色关系

- put请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/weight_role_relation/new_member_level_relations
```



- 参数

| 参数名    | 参数含义     |
| --------- | ------------ |
| roleId    | 角色id       |
| weightIds | 权限id，数组 |



- 示例

```json
{
    "roleId": 11,
    "weightIds": [11, 12]
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.6 UserRoleRelationController

##### 1.6.1 批量新增uid，角色关系，内部微服务调用

- put请求，请求体传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/user_role_relation/new_user_role_relation
```



- 参数

| 参数名  | 参数含义     |
| ------- | ------------ |
| uid     | uid          |
| roleIds | 角色id，数组 |



- 示例

```json
{
    "uid": "1642067605873348610",
    "roleIds": [3, 4]
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 1.7 MemberLevelDictController

##### 1.7.1 获取用户等级信息

- get请求

```bash
$baseUrl/member/member_level_dict/level_infos
```



- 响应结果

```json
{
    "result": [
        {
            "memberLevelId": 1,
            "roleId": 11,
            "roleType": "普通会员",
            "price": 0.00,
            "level": 0,
            "createTime": "2023-05-02T14:22:53",
            "updateTime": "2023-05-02T14:22:57"
        },
        {
            "memberLevelId": 4,
            "roleId": 15,
            "roleType": "1级会员",
            "price": 10.00,
            "level": 1,
            "createTime": "2023-07-26T16:09:56",
            "updateTime": "2023-07-26T16:09:56"
        },
        {
            "memberLevelId": 5,
            "roleId": 16,
            "roleType": "2级会员",
            "price": 15.00,
            "level": 2,
            "createTime": "2023-07-26T16:10:09",
            "updateTime": "2023-07-26T16:10:09"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.7.2 管理员添加用户等级信息

- put请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/member/member_level_dict/new_level
```



- 参数

| 参数名   | 参数含义        |
| -------- | --------------- |
| roleType | 角色名          |
| price    | 开通价格/月     |
| level    | 等级排序，最低0 |



- 示例

```json
{
    "roleType": "普通会员",
    "price": 15,
    "level": 3
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.7.3 根据memberLevelId获取角色名

- get请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/member/member_level_dict/role_type_by_member_level_id
```



- 参数

| 参数名        | 参数含义   |
| ------------- | ---------- |
| memberLevelId | 会员等级id |



- 示例

```bash
$baseUrl/member/member_level_dict/role_type_by_member_level_id?memberLevelId=1
```



- 响应结果

```json
{
    "result": "普通会员",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 1.7.4 根据memberLevelId获取用户等级信息

- get请求，url传参

```bash
$baseUrl/member/member_level_dict/member_level_dict_by_member_level_id
```



- 参数

| 参数名        | 参数含义   |
| ------------- | ---------- |
| memberLevelId | 会员等级id |



- 示例

```bash
$baseUrl/member/member_level_dict/member_level_dict_by_member_level_id?memberLevelId=1
```



- 响应结果

```json
{
    "result": {
        "memberLevelId": 1,
        "roleId": 11,
        "roleType": "普通会员",
        "price": 0.00,
        "level": 0,
        "createTime": "2023-05-02T14:22:53",
        "updateTime": "2023-05-02T14:22:57"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



### 2 video视频微服务

#### 2.1 CategoryInfoController

##### 2.1.1 获取层级影视分类信息

- get请求

```bash
$baseUrl/video/category_Info/level_category_info
```



- 响应格式
- level只有0-1

```json
{
    "result": [
        {
            "categoryId": 1,
            "parentId": null,
            "level": 0,
            "categoryName": "电影",
            "createTime": "2023-03-29T15:46:35",
            "updateTime": "2023-03-29T15:46:37",
            "child": [
                {
                    "categoryId": 26,
                    "parentId": 1,
                    "level": 1,
                    "categoryName": "科幻",
                    "createTime": "2023-05-01T14:37:42",
                    "updateTime": "2023-05-01T14:37:42",
                    "child": null
                },
                {
                    "categoryId": 27,
                    "parentId": 1,
                    "level": 1,
                    "categoryName": "喜剧",
                    "createTime": "2023-07-16T10:24:43",
                    "updateTime": "2023-07-16T10:24:45",
                    "child": null
                },
                {
                    "categoryId": 28,
                    "parentId": 1,
                    "level": 1,
                    "categoryName": "推理",
                    "createTime": "2023-07-16T10:25:16",
                    "updateTime": "2023-07-16T10:25:18",
                    "child": null
                }
            ]
        },
        {
            "categoryId": 2,
            "parentId": null,
            "level": 0,
            "categoryName": "电视剧",
            "createTime": "2023-03-29T15:52:30",
            "updateTime": "2023-03-29T15:52:32",
            "child": [
                {
                    "categoryId": 29,
                    "parentId": 2,
                    "level": 1,
                    "categoryName": "科幻",
                    "createTime": "2023-07-16T10:25:44",
                    "updateTime": "2023-07-16T10:25:46",
                    "child": null
                },
                {
                    "categoryId": 30,
                    "parentId": 2,
                    "level": 1,
                    "categoryName": "喜剧",
                    "createTime": "2023-07-16T10:26:01",
                    "updateTime": "2023-07-16T10:26:03",
                    "child": null
                },
                {
                    "categoryId": 31,
                    "parentId": 2,
                    "level": 1,
                    "categoryName": "推理",
                    "createTime": "2023-07-16T10:26:13",
                    "updateTime": "2023-07-16T10:26:15",
                    "child": null
                }
            ]
        },
        {
            "categoryId": 3,
            "parentId": null,
            "level": 0,
            "categoryName": "纪录片",
            "createTime": "2023-03-29T15:54:05",
            "updateTime": "2023-03-29T15:54:07",
            "child": [
                {
                    "categoryId": 32,
                    "parentId": 3,
                    "level": 1,
                    "categoryName": "地理",
                    "createTime": "2023-07-16T10:26:34",
                    "updateTime": "2023-07-16T10:26:36",
                    "child": null
                },
                {
                    "categoryId": 33,
                    "parentId": 3,
                    "level": 1,
                    "categoryName": "动物",
                    "createTime": "2023-07-16T10:27:10",
                    "updateTime": "2023-07-16T10:27:13",
                    "child": null
                }
            ]
        },
        {
            "categoryId": 23,
            "parentId": null,
            "level": 0,
            "categoryName": "animate",
            "createTime": "2023-04-25T14:12:55",
            "updateTime": "2023-04-25T14:12:55",
            "child": [
                {
                    "categoryId": 24,
                    "parentId": 23,
                    "level": 1,
                    "categoryName": "战斗",
                    "createTime": "2023-04-25T14:13:22",
                    "updateTime": "2023-04-25T14:13:22",
                    "child": null
                },
                {
                    "categoryId": 25,
                    "parentId": 23,
                    "level": 1,
                    "categoryName": "恋爱",
                    "createTime": "2023-04-25T14:13:30",
                    "updateTime": "2023-04-25T14:13:30",
                    "child": null
                },
                {
                    "categoryId": 34,
                    "parentId": 23,
                    "level": 1,
                    "categoryName": "校园",
                    "createTime": "2023-07-16T10:27:53",
                    "updateTime": "2023-07-16T10:28:04",
                    "child": null
                }
            ]
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.1.2 管理员新增影视分类信息

- put请求，请求体传参
- 需要admin权限
- 需要登录状态

```bash
$baseUrl/video/category_Info/new_category_info
```



- 参数

| 参数名       | 参数含义                           |
| ------------ | ---------------------------------- |
| parentId     | 父分类id，如果是根节点则不传此参数 |
| level        | 分类层级，0，1，最大为1            |
| categoryName | 分类名                             |



- 示例

```json
{
    "parentId": 1,
    "level": 0,
    "categoryName": "喜剧"
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.1.3 管理员删除叶节点影视分类信息

- delete请求，url传参
- 需要admin权限
- 需要登录状态
- 只能删除叶节点

```bash
$category/video/category_Info/delete_leaf_category_info
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| categoryId | 分类id   |



- 示例

```bash
$category/video/category_Info/delete_leaf_category_info?categoryId=28
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.2 VideoInfoController

##### 2.2.1 管理员添加影视信息

- put请求，form-data上传文件
- 需要admin角色

```bash
$baseUrl/video/video_info/new_video_info
```



- 参数

| 参数名           | 参数含义 |
| ---------------- | -------- |
| file             | 文件     |
| videoName        | 影视名   |
| videoDescription | 影视描述 |
| videoProducer    | 制作人   |
| videoActors      | 演员信息 |
| categoryId       | 分类id   |
| upTime           | 上映时间 |



- 响应结果

```bash
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.2 管理员上架影视视频

- post请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/video_info/up_video
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| videoInfoId | 影视信息id |



- 示例

```bash
$baseUrl/video/video_info/up_video?videoInfoId=1680781825485639682
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.3 管理员分页查询影视视频信息

- post请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/video_info/video_infos
```



- 参数

| 参数名      | 参数含义                                             |
| ----------- | ---------------------------------------------------- |
| queryWords  | 查询关键字，可选                                     |
| currentPage | 当前页数                                             |
| limited     | 每页数量                                             |
| objectId    | 影视视频信息状态，可选，-1->下架，0->上架，1->回收站 |



- 示例

```json
{
    "queryWords": "NEKO",
    "currentPage": 1,
    "limited": 8,
    "objectId": "-1"
}
```



- 响应结果

```json
{
    "result": {
        "records": [
            {
                "videoInfoId": "1680783166308159489",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/f75d6c13-e485-420b-8437-920261b9618f_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "deleteExpireTime": null,
                "status": -1,
                "createTime": "2023-07-17T11:35:09",
                "updateTime": "2023-07-17T11:35:09"
            },
            {
                "videoInfoId": "1680783148910186498",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/73580e48-e4a8-4c98-bef5-1582d63aa0cb_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "deleteExpireTime": null,
                "status": -1,
                "createTime": "2023-07-17T11:35:05",
                "updateTime": "2023-07-17T11:35:05"
            },
            {
                "videoInfoId": "1680783130899845122",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/0b88989f-13ac-4b4c-883e-c80f22510f1e_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "deleteExpireTime": null,
                "status": -1,
                "createTime": "2023-07-17T11:35:01",
                "updateTime": "2023-07-17T11:35:01"
            },
            {
                "videoInfoId": "1680781825485639682",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "deleteExpireTime": null,
                "status": -1,
                "createTime": "2023-07-17T11:29:49",
                "updateTime": "2023-07-17T11:29:49"
            }
        ],
        "total": 4,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.4 根据videoInfoId查询影视视频信息

- get请求，url传参

```bash
$baseUrl/video/video_info/video_info_by_video_info_id
```



- 参数

| 参数名      | 参数含义       |
| ----------- | -------------- |
| videoInfoId | 影视视频信息id |



- 示例

```bash
$baseUrl/video/video_info/video_info_by_video_info_id?videoInfoId=1680781825485639682
```



- 响应结果

```json
{
    "result": {
        "videoInfoId": "1680781825485639682",
        "videoName": "NEKO",
        "videoDescription": "NEKO",
        "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
        "videoProducer": "NEKO",
        "videoActors": "NEKO",
        "categoryId": 34,
        "categoryName": "校园",
        "upTime": "2023-03-29T15:46:35",
        "status": 0,
        "createTime": "2023-07-17T11:29:49",
        "updateTime": "2023-07-17T11:29:49"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.5 管理员下架影视视频

- post请求，url传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/video_info/down_video
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| videoInfoId | 影视信息id |



- 示例

```bash
$baseUrl/video/video_info/down_video?videoInfoId=1683373796746121218
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.6 管理员修改影视信息

- post请求，form-data上传文件
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/video_info/update_video_info
```



- 参数

| 参数名           | 参数含义     |
| ---------------- | ------------ |
| videoInfoId      | 影视信息id   |
| file             | 封面图，可选 |
| videoName        | 影视名，可选 |
| videoDescription | 影视描述     |
| videoProducer    | 制作人       |
| videoActors      | 演员信息     |
| categoryId       | 分类id       |
| upTime           | 上映时间     |



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.7 管理员将指定影视信息放入回收站中

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/video_info/delete_into_recycle_bin
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| videoInfoId | 影视信息id |



- 示例

```bash
$baseUrl/video/video_info/delete_into_recycle_bin?videoInfoId=1689443800025444354
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.8 管理员将指定影视信息直接删除

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/video_info/delete_video_info
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| videoInfoId | 影视信息id |



- 示例

```bash
$baseUrl/video/video_info/delete_video_info?videoInfoId=1689466743069667330
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.9 根据根分类id获取少量热门影视信息

- get请求，url传参

```bash
$baseUrl/video/video_info/top_video_info_by_root_category_id
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| categoryId | 分类id   |



- 示例

```bash
$baseUrl/video/video_info/top_video_info_by_root_category_id?categoryId=1
```



- 响应结果
- 最大10条

```json
{
    "result": [
        {
            "videoInfoId": "1689450889653301249",
            "videoName": "誓约",
            "videoDescription": "《誓约》是2012年的一部美国浪漫电影。导演是迈克尔·苏克西，由瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔等主演。影片改编自真实故事。于2011年8月至11月拍摄。2012年2月首映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/2cc8206e-f195-4d7c-9d37-eaebb7cea5a6_the-vow-2.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "迈克尔·苏克西",
            "videoActors": "瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔",
            "upTime": "2012-02-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689451900174381058",
            "videoName": "第五波",
            "videoDescription": "《第五波》是2016年的一部美国科幻动作电影，由J·布莱克森执导，以及由苏珊娜·葛兰特、阿奇瓦·高斯曼及杰夫·平克纳担任编剧，改编自瑞克·杨西于2013年的同名小说。电影由科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖伯等人主演。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/848de928-0a90-4191-8242-a309de173d04_MV5BOTI5NDQ2N2ItZTAyMi00YjIxLTkzMWMtN2UzODJlN2M2YTYyXkEyXkFqcGdeQXVyNzExMzc0MDg@._V1_.jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "J·布莱克森",
            "videoActors": "科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖",
            "upTime": "2016-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689454145213345793",
            "videoName": "全面回忆",
            "videoDescription": "《全面回忆》是一部2012年美国科幻动作片，根据1990年的英文同名电影和菲利普·狄克的1966年中篇小说《We Can Remember It for You Wholesale》改编而成。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/9ec42129-61f5-4047-8ef2-d09dd679ff11_total-recall-movie (21).jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "保罗·范霍文",
            "videoActors": "菲利普·迪克 罗纳德·舒塞特 丹·欧班农 Jon Povill 加里·戈德曼 雷切尔·蒂科汀 莎朗·斯通 罗尼·考克斯 迈克尔·艾恩塞德 马绍尔·贝尔 梅尔约翰逊 迈克尔切姆平 罗伊·布罗克斯史密斯 雷·贝克 萝丝玛丽·邓斯莫尔 戴维·奈尔 阿列卡夏罗宾逊 迪恩·诺里斯 马克卡尔顿 黛比·李·卡林顿 Lycia Naff 罗伯特·康斯坦佐 Michael LaGuardia Priscilla Allen 马克·阿莱莫 迈克尔·格雷戈里",
            "upTime": "2012-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689455440183734273",
            "videoName": "最长的旅程",
            "videoDescription": "《最长的旅程》是一部美国2015年浪漫剧情片，为小乔治·提曼所执导，克雷格·波顿编剧。电影改编自尼可拉斯·史派克的2013年小说《The Longest Ride》。由布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾主演。为二十世紀福克斯负责发行，美国于2015年4月10日上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/0a7cde10-d827-44f3-a908-05a646ee157f_69917b294a20135e07043db1e09b27e5.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "小乔治·提曼",
            "videoActors": "布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾",
            "upTime": "2015-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689457959261745154",
            "videoName": "蜘蛛侠：纵横宇宙",
            "videoDescription": "《蜘蛛侠：纵横宇宙》（英语：Spider-Man: Across the Spider-Verse，香港译《蜘蛛侠：飞跃蜘蛛宇宙》，台湾译《蜘蛛人：穿越新宇宙》）是一部2023年美国电脑动画超级英雄电影，2018年电影《蜘蛛侠：平行宇宙》的续集，亦是《蜘蛛宇宙系列》的第二部电影。本片由瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森共同执导，大卫·卡拉汉、菲尔·罗德与克里斯托弗·米勒共同撰写剧本；声演阵容包括沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克。本片2023年6月2日在美国上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/fce67f1f-0d2c-4c9f-a5cf-4ce53b3c3179_spider-man-into-the-spider-verse-miles-morales-spider-man-2880x1800-2948.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克",
            "upTime": "2023-06-02 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689463385999040514",
            "videoName": "蜘蛛侠：平行宇宙",
            "videoDescription": "《蜘蛛侠：平行宇宙》（英语：Spider-Man: Into the Spider-Verse）是一部于2018年上映的美国电脑动画超级英雄电影，本片为蜘蛛宇宙系列的第一部电影，由哥伦比亚影业、漫威娱乐和索尼动画联合制作，并由索尼影视娱乐负责发行。本片由鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼执导，并由菲尔·罗德与克里斯托弗·米勒撰写剧本，剧情围绕着由布瑞恩·马克·班迪斯与莎拉·皮谢利创作的漫威漫画角色迈尔斯·莫拉莱斯／蜘蛛侠展开。本片由沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森担任主要配音员。\r\n\r\n由菲尔·罗德和克里斯托弗·米勒共同筹拍的一部蜘蛛侠动画电影的计划于2014年首度公开，并于2015年4月受到证实。沙梅克·摩尔与列夫·施赖伯于2017年4月加入剧组，而马赫沙拉·阿里和布莱恩·泰里·亨利则于同年6月签约参演。\r\n\r\n本片的片名于12月公布。该片于2018年12月14日在美国上映。电影发行后口碑和票房双收，动画效果、动作场面、角色设计、编剧皆获得正面评价，本片同时获颁众多奖项，这其中包含第91届奥斯卡金像奖的最佳动画长片奖、第46届安妮奖的七项奖项、第76届金球奖最佳动画。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/803380b7-a437-4c28-a038-4a0d96587fd7_994927.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森",
            "upTime": "2018-12-04 00:00:00.0",
            "playNumber": 0
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.10 根据根分类id获取大量热门影视信息

- get请求，url传参

```bash
$baseUrl/video/video_info/top_plenty_video_info_by_root_category_id
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| categoryId | 分类id   |



- 示例

```bash
$baseUrl/video/video_info/top_plenty_video_info_by_root_category_id?categoryId=1
```



- 响应结果
- 最大20条

```json
{
    "result": [
        {
            "videoInfoId": "1689450889653301249",
            "videoName": "誓约",
            "videoDescription": "《誓约》是2012年的一部美国浪漫电影。导演是迈克尔·苏克西，由瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔等主演。影片改编自真实故事。于2011年8月至11月拍摄。2012年2月首映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/2cc8206e-f195-4d7c-9d37-eaebb7cea5a6_the-vow-2.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "迈克尔·苏克西",
            "videoActors": "瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔",
            "upTime": "2012-02-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689451900174381058",
            "videoName": "第五波",
            "videoDescription": "《第五波》是2016年的一部美国科幻动作电影，由J·布莱克森执导，以及由苏珊娜·葛兰特、阿奇瓦·高斯曼及杰夫·平克纳担任编剧，改编自瑞克·杨西于2013年的同名小说。电影由科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖伯等人主演。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/848de928-0a90-4191-8242-a309de173d04_MV5BOTI5NDQ2N2ItZTAyMi00YjIxLTkzMWMtN2UzODJlN2M2YTYyXkEyXkFqcGdeQXVyNzExMzc0MDg@._V1_.jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "J·布莱克森",
            "videoActors": "科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖",
            "upTime": "2016-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689454145213345793",
            "videoName": "全面回忆",
            "videoDescription": "《全面回忆》是一部2012年美国科幻动作片，根据1990年的英文同名电影和菲利普·狄克的1966年中篇小说《We Can Remember It for You Wholesale》改编而成。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/9ec42129-61f5-4047-8ef2-d09dd679ff11_total-recall-movie (21).jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "保罗·范霍文",
            "videoActors": "菲利普·迪克 罗纳德·舒塞特 丹·欧班农 Jon Povill 加里·戈德曼 雷切尔·蒂科汀 莎朗·斯通 罗尼·考克斯 迈克尔·艾恩塞德 马绍尔·贝尔 梅尔约翰逊 迈克尔切姆平 罗伊·布罗克斯史密斯 雷·贝克 萝丝玛丽·邓斯莫尔 戴维·奈尔 阿列卡夏罗宾逊 迪恩·诺里斯 马克卡尔顿 黛比·李·卡林顿 Lycia Naff 罗伯特·康斯坦佐 Michael LaGuardia Priscilla Allen 马克·阿莱莫 迈克尔·格雷戈里",
            "upTime": "2012-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689455440183734273",
            "videoName": "最长的旅程",
            "videoDescription": "《最长的旅程》是一部美国2015年浪漫剧情片，为小乔治·提曼所执导，克雷格·波顿编剧。电影改编自尼可拉斯·史派克的2013年小说《The Longest Ride》。由布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾主演。为二十世紀福克斯负责发行，美国于2015年4月10日上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/0a7cde10-d827-44f3-a908-05a646ee157f_69917b294a20135e07043db1e09b27e5.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "小乔治·提曼",
            "videoActors": "布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾",
            "upTime": "2015-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689457959261745154",
            "videoName": "蜘蛛侠：纵横宇宙",
            "videoDescription": "《蜘蛛侠：纵横宇宙》（英语：Spider-Man: Across the Spider-Verse，香港译《蜘蛛侠：飞跃蜘蛛宇宙》，台湾译《蜘蛛人：穿越新宇宙》）是一部2023年美国电脑动画超级英雄电影，2018年电影《蜘蛛侠：平行宇宙》的续集，亦是《蜘蛛宇宙系列》的第二部电影。本片由瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森共同执导，大卫·卡拉汉、菲尔·罗德与克里斯托弗·米勒共同撰写剧本；声演阵容包括沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克。本片2023年6月2日在美国上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/fce67f1f-0d2c-4c9f-a5cf-4ce53b3c3179_spider-man-into-the-spider-verse-miles-morales-spider-man-2880x1800-2948.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克",
            "upTime": "2023-06-02 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689463385999040514",
            "videoName": "蜘蛛侠：平行宇宙",
            "videoDescription": "《蜘蛛侠：平行宇宙》（英语：Spider-Man: Into the Spider-Verse）是一部于2018年上映的美国电脑动画超级英雄电影，本片为蜘蛛宇宙系列的第一部电影，由哥伦比亚影业、漫威娱乐和索尼动画联合制作，并由索尼影视娱乐负责发行。本片由鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼执导，并由菲尔·罗德与克里斯托弗·米勒撰写剧本，剧情围绕着由布瑞恩·马克·班迪斯与莎拉·皮谢利创作的漫威漫画角色迈尔斯·莫拉莱斯／蜘蛛侠展开。本片由沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森担任主要配音员。\r\n\r\n由菲尔·罗德和克里斯托弗·米勒共同筹拍的一部蜘蛛侠动画电影的计划于2014年首度公开，并于2015年4月受到证实。沙梅克·摩尔与列夫·施赖伯于2017年4月加入剧组，而马赫沙拉·阿里和布莱恩·泰里·亨利则于同年6月签约参演。\r\n\r\n本片的片名于12月公布。该片于2018年12月14日在美国上映。电影发行后口碑和票房双收，动画效果、动作场面、角色设计、编剧皆获得正面评价，本片同时获颁众多奖项，这其中包含第91届奥斯卡金像奖的最佳动画长片奖、第46届安妮奖的七项奖项、第76届金球奖最佳动画。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/803380b7-a437-4c28-a038-4a0d96587fd7_994927.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森",
            "upTime": "2018-12-04 00:00:00.0",
            "playNumber": 0
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.11 获取最近上映影视信息

- get请求

```bash
$baseUrl/video/video_info/recent_up
```



- 响应结果

```json
{
    "result": [
        {
            "videoInfoId": "1689463385999040514",
            "videoName": "蜘蛛侠：平行宇宙",
            "videoDescription": "《蜘蛛侠：平行宇宙》（英语：Spider-Man: Into the Spider-Verse）是一部于2018年上映的美国电脑动画超级英雄电影，本片为蜘蛛宇宙系列的第一部电影，由哥伦比亚影业、漫威娱乐和索尼动画联合制作，并由索尼影视娱乐负责发行。本片由鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼执导，并由菲尔·罗德与克里斯托弗·米勒撰写剧本，剧情围绕着由布瑞恩·马克·班迪斯与莎拉·皮谢利创作的漫威漫画角色迈尔斯·莫拉莱斯／蜘蛛侠展开。本片由沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森担任主要配音员。\r\n\r\n由菲尔·罗德和克里斯托弗·米勒共同筹拍的一部蜘蛛侠动画电影的计划于2014年首度公开，并于2015年4月受到证实。沙梅克·摩尔与列夫·施赖伯于2017年4月加入剧组，而马赫沙拉·阿里和布莱恩·泰里·亨利则于同年6月签约参演。\r\n\r\n本片的片名于12月公布。该片于2018年12月14日在美国上映。电影发行后口碑和票房双收，动画效果、动作场面、角色设计、编剧皆获得正面评价，本片同时获颁众多奖项，这其中包含第91届奥斯卡金像奖的最佳动画长片奖、第46届安妮奖的七项奖项、第76届金球奖最佳动画。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/803380b7-a437-4c28-a038-4a0d96587fd7_994927.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森",
            "upTime": "2018-12-04 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689457959261745154",
            "videoName": "蜘蛛侠：纵横宇宙",
            "videoDescription": "《蜘蛛侠：纵横宇宙》（英语：Spider-Man: Across the Spider-Verse，香港译《蜘蛛侠：飞跃蜘蛛宇宙》，台湾译《蜘蛛人：穿越新宇宙》）是一部2023年美国电脑动画超级英雄电影，2018年电影《蜘蛛侠：平行宇宙》的续集，亦是《蜘蛛宇宙系列》的第二部电影。本片由瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森共同执导，大卫·卡拉汉、菲尔·罗德与克里斯托弗·米勒共同撰写剧本；声演阵容包括沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克。本片2023年6月2日在美国上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/fce67f1f-0d2c-4c9f-a5cf-4ce53b3c3179_spider-man-into-the-spider-verse-miles-morales-spider-man-2880x1800-2948.jpg",
            "categoryId": 28,
            "categoryName": "战斗",
            "videoProducer": "瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森",
            "videoActors": "沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克",
            "upTime": "2023-06-02 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689455440183734273",
            "videoName": "最长的旅程",
            "videoDescription": "《最长的旅程》是一部美国2015年浪漫剧情片，为小乔治·提曼所执导，克雷格·波顿编剧。电影改编自尼可拉斯·史派克的2013年小说《The Longest Ride》。由布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾主演。为二十世紀福克斯负责发行，美国于2015年4月10日上映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/0a7cde10-d827-44f3-a908-05a646ee157f_69917b294a20135e07043db1e09b27e5.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "小乔治·提曼",
            "videoActors": "布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾",
            "upTime": "2015-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689454145213345793",
            "videoName": "全面回忆",
            "videoDescription": "《全面回忆》是一部2012年美国科幻动作片，根据1990年的英文同名电影和菲利普·狄克的1966年中篇小说《We Can Remember It for You Wholesale》改编而成。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/9ec42129-61f5-4047-8ef2-d09dd679ff11_total-recall-movie (21).jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "保罗·范霍文",
            "videoActors": "菲利普·迪克 罗纳德·舒塞特 丹·欧班农 Jon Povill 加里·戈德曼 雷切尔·蒂科汀 莎朗·斯通 罗尼·考克斯 迈克尔·艾恩塞德 马绍尔·贝尔 梅尔约翰逊 迈克尔切姆平 罗伊·布罗克斯史密斯 雷·贝克 萝丝玛丽·邓斯莫尔 戴维·奈尔 阿列卡夏罗宾逊 迪恩·诺里斯 马克卡尔顿 黛比·李·卡林顿 Lycia Naff 罗伯特·康斯坦佐 Michael LaGuardia Priscilla Allen 马克·阿莱莫 迈克尔·格雷戈里",
            "upTime": "2012-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689451900174381058",
            "videoName": "第五波",
            "videoDescription": "《第五波》是2016年的一部美国科幻动作电影，由J·布莱克森执导，以及由苏珊娜·葛兰特、阿奇瓦·高斯曼及杰夫·平克纳担任编剧，改编自瑞克·杨西于2013年的同名小说。电影由科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖伯等人主演。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/848de928-0a90-4191-8242-a309de173d04_MV5BOTI5NDQ2N2ItZTAyMi00YjIxLTkzMWMtN2UzODJlN2M2YTYyXkEyXkFqcGdeQXVyNzExMzc0MDg@._V1_.jpg",
            "categoryId": 26,
            "categoryName": "科幻",
            "videoProducer": "J·布莱克森",
            "videoActors": "科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖",
            "upTime": "2016-01-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1689450889653301249",
            "videoName": "誓约",
            "videoDescription": "《誓约》是2012年的一部美国浪漫电影。导演是迈克尔·苏克西，由瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔等主演。影片改编自真实故事。于2011年8月至11月拍摄。2012年2月首映。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/2cc8206e-f195-4d7c-9d37-eaebb7cea5a6_the-vow-2.jpg",
            "categoryId": 27,
            "categoryName": "恋爱",
            "videoProducer": "迈克尔·苏克西",
            "videoActors": "瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔",
            "upTime": "2012-02-01 00:00:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1683373796746121218",
            "videoName": "鬼灭之刃 锻刀村篇",
            "videoDescription": "卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG",
            "categoryId": 24,
            "categoryName": "战斗",
            "videoProducer": "外崎春雄",
            "videoActors": "花江夏树 下野纮 松冈祯丞 鬼头明里 宫野真守 花泽香菜 河西健吾",
            "upTime": "2023-07-24 15:09:00.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1683374868122357762",
            "videoName": "鬼灭之刃 游郭篇",
            "videoDescription": "鬼灭之刃游郭篇故事剧情将从《鬼灭之刃无限列车篇》失去炎柱后做为开端，以日本花街为舞台，而最让人期待的音柱、堕姬即将在鬼灭第二季登场！",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/032df54a-fb38-40da-a64d-893223e20e4d_20230411164723_513.jpg",
            "categoryId": 24,
            "categoryName": "战斗",
            "videoProducer": "外崎春雄",
            "videoActors": "花江夏树 鬼头明里 下野纮 松冈祯丞 小西克幸 森川智之 泽城美雪 逢坂良太 石上静香 东山奈央 种崎敦美 关俊彦 浪川大辅 上田丽奈 江原裕理 石田彰",
            "upTime": "2023-07-24 15:12:27.0",
            "playNumber": 0
        },
        {
            "videoInfoId": "1682551371754180610",
            "videoName": "花神之舞",
            "videoDescription": "妮露 花神之舞",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/62286bc7-6c92-4dc1-80e4-71c2fefca495_捕获.PNG",
            "categoryId": 24,
            "categoryName": "战斗",
            "videoProducer": "mihoyo",
            "videoActors": "妮露",
            "upTime": "2023-07-22 08:40:46.0",
            "playNumber": 0
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.2.12 管理员上架全部影视视频

- post请求
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/video_info/up_all_video
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.3 ElasticSearchController

##### 2.3.1 分页查询查询影视信息

- post请求，请求体传参

```bash
$baseUrl/video/elastic_search/product_infos
```



- 参数

| 参数名      | 参数含义           |
| ----------- | ------------------ |
| videoInfoId | 影视信息id，可选   |
| categoryId  | 分类id，可选       |
| minTime     | 最早上映时间，可选 |
| maxTime     | 最晚上映时间，可选 |
| queryWords  | 查询关键字，可选   |
| currentPage | 当前页数           |
| limited     | 每页数量           |



- 示例

```json
{
    "videoInfoId": "1680781825485639682",
    "categoryId": 34,
    "minTime": "2023-07-17 11:29:49",
    "maxTime": "2023-07-17 11:35:41",
    "queryWords": "NEKO",
    "currentPage": 1,
    "limited": 8
}
```



- 响应结果

```json
{
    "result": {
        "records": [
            {
                "videoInfoId": "1682551371754180610",
                "videoName": "花神之舞",
                "videoDescription": "妮露 花神之舞",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/62286bc7-6c92-4dc1-80e4-71c2fefca495_捕获.PNG",
                "categoryId": 24,
                "categoryName": "战斗",
                "videoProducer": "mihoyo",
                "videoActors": "妮露",
                "upTime": "2023-07-22 08:40:46",
                "playNumber": 0
            },
            {
                "videoInfoId": "1683373796746121218",
                "videoName": "鬼灭之刃 锻刀村篇",
                "videoDescription": "卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG",
                "categoryId": 24,
                "categoryName": "战斗",
                "videoProducer": "外崎春雄",
                "videoActors": "花江夏树 下野纮 松冈祯丞 鬼头明里 宫野真守 花泽香菜 河西健吾",
                "upTime": "2023-07-24 15:09:00",
                "playNumber": 0
            },
            {
                "videoInfoId": "1683374868122357762",
                "videoName": "鬼灭之刃 游郭篇",
                "videoDescription": "鬼灭之刃游郭篇故事剧情将从《鬼灭之刃无限列车篇》失去炎柱后做为开端，以日本花街为舞台，而最让人期待的音柱、堕姬即将在鬼灭第二季登场！",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/032df54a-fb38-40da-a64d-893223e20e4d_20230411164723_513.jpg",
                "categoryId": 24,
                "categoryName": "战斗",
                "videoProducer": "外崎春雄",
                "videoActors": "花江夏树 鬼头明里 下野纮 松冈祯丞 小西克幸 森川智之 泽城美雪 逢坂良太 石上静香 东山奈央 种崎敦美 关俊彦 浪川大辅 上田丽奈 江原裕理 石田彰",
                "upTime": "2023-07-24 15:12:27",
                "playNumber": 0
            }
        ],
        "total": 3,
        "size": 8,
        "current": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.3.2 获取按照影视信息分类聚合饼图信息

- get请求

```bash
$baseUrl/video/elastic_search/category_agg_pie
```



- 响应结果

```json
{
    "result": [
        {
            "value": 15,
            "name": "恋爱"
        },
        {
            "value": 15,
            "name": "战斗"
        },
        {
            "value": 14,
            "name": "科幻"
        },
        {
            "value": 7,
            "name": "喜剧"
        },
        {
            "value": 7,
            "name": "推理"
        },
        {
            "value": 7,
            "name": "校园"
        },
        {
            "value": 6,
            "name": "动物"
        },
        {
            "value": 6,
            "name": "地理"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.4 VideoSeriesInfoController

##### 2.4.1 管理员分页查询指定videoSeriesId视频分集信息

- post请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/video_series_info/admin_video_series_infos
```



- 参数

| 参数名      | 参数含义                                           |
| ----------- | -------------------------------------------------- |
| queryWords  | 查询关键字，可选，指定后可按集数查询               |
| currentPage | 当前页数                                           |
| limited     | 每页数量                                           |
| objectId    | 影视视频信息id，为要查询视频分集所属影视视频信息id |



- 示例

```json
{
    "queryWords": "1",
    "currentPage": 1,
    "limited": 8,
    "objectId": "1682551371754180610"
}
```



- 响应结果

```json
{
    "result": {
        "records": [
            {
                "videoSeriesId": "1682564048132014082",
                "videoInfoId": "1682551371754180610",
                "seriesNumber": 1,
                "videoUrl": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/89a91135-f3f3-4147-9ef6-2240702d1acb_nilou.mp4",
                "weightId": 16,
                "weightType": "基本视频观看",
                "createTime": "2023-07-22T09:31:44",
                "updateTime": "2023-07-22T09:31:44"
            }
        ],
        "total": 1,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.4.2 管理员添加视频分集集数

- put请求，form-data上传文件
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/video_series_info/new_video_series
```



- 参数

| 参数名       | 参数含义                   |
| ------------ | -------------------------- |
| file         | 文件                       |
| videoInfoId  | 集数所属影视信息id         |
| seriesNumber | 集数                       |
| weightId     | 观看所需会员等级类型权限id |



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.4.3 根据影视集数id获取影视单集信息

- post请求，url传参
- 需要登录状态

```bash
$baseUrl/video/video_series_info/video_series_info_by_id
```



- 参数

| 参数名        | 参数含义   |
| ------------- | ---------- |
| videoSeriesId | 影视集数id |



- 示例

```bash
$baseUrl/video/video_series_info/video_series_info_by_id?videoSeriesId=1682564048132014082
```



- 响应结果

```json
{
    "result": {
        "videoSeriesId": "1682564048132014082",
        "videoInfoId": "1682551371754180610",
        "seriesNumber": 1,
        "videoUrl": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/89a91135-f3f3-4147-9ef6-2240702d1acb_nilou.mp4",
        "weightId": 16,
        "weightType": "基本视频观看",
        "createTime": "2023-07-22T09:31:44",
        "updateTime": "2023-07-22T09:31:44"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.4.4 查询指定videoSeriesId全部视频分集信息

- get请求，url传参

```bash
$baseUrl/video/video_series_info/video_series_infos
```



- 参数

| 参数名      | 参数含义           |
| ----------- | ------------------ |
| videoInfoId | 集数所属影视信息id |



- 示例

```bash
$baseUrl/video/video_series_info/video_series_infos?videoInfoId=1683373796746121218
```



- 响应结果

```json
{
    "result": [
        {
            "videoSeriesId": "1683373881982767105",
            "videoInfoId": "1683373796746121218",
            "seriesNumber": 5,
            "weightId": 16,
            "weightType": "基本视频观看",
            "createTime": "2023-07-24T15:09:44",
            "updateTime": "2023-07-24T15:09:44"
        },
        {
            "videoSeriesId": "1683374990189187074",
            "videoInfoId": "1683373796746121218",
            "seriesNumber": 6,
            "weightId": 16,
            "weightType": "基本视频观看",
            "createTime": "2023-07-24T15:14:08",
            "updateTime": "2023-07-24T15:14:08"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.4.5 管理员删除指定videoSeriesId视频分集信息

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/video_series_info/delete_video_series_info
```



- 参数

| 参数名        | 参数含义   |
| ------------- | ---------- |
| videoSeriesId | 影视集数id |



- 示例

```bash
$baseUrl/video/video_series_info/delete_video_series_info?videoSeriesId=1689074341590896642
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.5 OrderInfoController

##### 2.5.1 获取预生成订单token，保证预生成订单接口幂等性

- get请求
- 需要登录状态
- 此接口获取的token用于提交订单接口处参数使用，保证提交订单接口幂等性

```bash
$baseUrl/video/order_info/preorder_token
```



- 响应结果

```json
{
    "result": "202307271014218061684386714539352066",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.5.1 提交订单

- put请求，请求体传参
- 需要登录状态

```bash
$baseUrl/video/order_info/new_order
```



- 参数

| 参数名         | 参数含义                                                     |
| -------------- | ------------------------------------------------------------ |
| token          | 2.5.1接口获取，用于保证提交订单接口幂等性，订单提交后为订单号 |
| discountId     | 秒杀折扣id                                                   |
| memberLevelId  | 要开通的会员等级id                                           |
| payLevelMonths | 购买月数                                                     |



- 示例

```json
{
    "token": "202307281615561901684840095271706625",
    "discountId": "1684813417543634945",
    "memberLevelId": 4,
    "payLevelMonths": 1
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.5.2 根据订单号获取支付宝支付页面

- get请求，url传参
- 需要登录状态

```bash
$baseUrl/video/order_info/alipay_page
```



- 参数

| 参数名  | 参数含义  |
| ------- | --------- |
| orderId | 订单号    |
| token   | 用户token |



- 示例

```bash
$baseUrl/video/order_info/alipay_page?orderId=202307290849512571685090222724939777&token=cec15363-7d2b-43e4-9239-2677b15730a2
```



- 沙箱账号

```bash
hbwmin1080@sandbox.com
```



- 沙箱密码

```bash
111111
```



- 响应结果
- 结果为一个页面

```bash
<form name="punchout_form" method="post"
	action="https://openapi-sandbox.dl.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=LxxZasUoreyszVfOpYjMwCXfnV3ttgNj238Q57wzKxc1fGly%2Bfbmn73EMSv%2FCtMefwYlb4%2ByJiSIc%2B9h%2FhA8OUDQw959uSR0I014uwI5KMEvF%2BsOFPi9zhnmqjmv%2FnkxcCylMiNOB4QpBHBkXEaqRfp8Fs6J1mfQivnI%2B5PlGx1AC77h1rADeG4xCk47knURBN9xNy%2B3TNVzSEU58mGNAbaezxWmNqR42px8jBbi%2BY90ZwjpHpxTIpmln%2F5m%2F%2FgRzDLLQOuypUbq7JZEY6eN3aNuOr0OrX0fO2ajhkJvkRehcByvOcm5I%2BT7deXIr4shB7Txg%2BaakkcdKBGA4rDTig%3D%3D&return_url=http%3A%2F%2Flocalhost%3A8080%2F%23%2Forder_complete&notify_url=http%3A%2F%2Fxet6em.natappfree.cc%2Forder%2Forder_info%2Falipay_listener&version=1.0&app_id=2021000122638851&sign_type=RSA2&timestamp=2023-07-29+08%3A42%3A42&alipay_sdk=alipay-sdk-java-4.38.34.ALL&format=json">
	<input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;202307290842359721685088397175095298&quot;,&quot;total_amount&quot;:&quot;10.00&quot;,&quot;subject&quot;:&quot;NEKO_MOVIE&quot;,&quot;body&quot;:&quot;NEKO_MOVIE&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
	<input type="submit" value="立即支付" style="display:none" >
</form>
	<script>
		document.forms[0].submit();
	</script>
```



##### 2.5.3 根据订单号获取未取消订单信息

- get请求，url传参
- 需要登录状态

```bash
$baseUrl/video/order_info/order_info_by_order_id
```



- 参数

| 参数名  | 参数含义 |
| ------- | -------- |
| orderId | 订单号   |



- 示例

```bash
$baseUrl/video/order_info/order_info_by_order_id?orderId=202307311005427071685834088755286017
```



- 响应结果
- status为订单状态，0->未支付，1->已支付

```json
{
    "result": {
        "orderId": "202307311005427071685834088755286017",
        "alipayTradeId": "2023073122001436790500485807",
        "uid": "1642067605873348610",
        "discountId": "1684813417543634945",
        "cost": 9.00,
        "actualCost": 9.00,
        "status": 1,
        "memberLevelId": 4,
        "roleType": "1级会员",
        "payLevelMonths": 1,
        "createTime": "2023-07-31T10:05:48",
        "updateTime": "2023-07-31T10:06:30"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.6 DiscountInfoController

##### 2.6.1 管理员添加折扣信息

- put请求，请求体传参
- 需要admin角色
- 需要登录状态

```bash
$baseUrl/video/discount_info/new_discount_info
```



- 参数

| 参数名       | 参数含义                      |
| ------------ | ----------------------------- |
| discountName | 秒杀折扣名                    |
| discountRate | 折扣百分比                    |
| number       | 限制数量                      |
| startTime    | 开始时间，必须晚于添加时间2天 |
| endTime      | 结束时间                      |



- 示例

```json
{
    "discountName": "会员折扣活动",
    "discountRate": 90,
    "number": 5,
    "startTime": "2023-07-30 11:35:41",
    "endTime": "2023-07-30 12:35:41"
}
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.6.2 获取2天内开始或已开始折扣信息

- get请求

```bash
$baseUrl/video/discount_info/two_days_or_available_discount_info
```



- 响应结果

> discountRate为折扣百分比
>
> number为限制数量

```json
{
    "result": {
        "discountId": "1684759662223716354",
        "discountName": "会员折扣活动",
        "discountRate": 90,
        "number": 5,
        "startTime": "2023-07-30T11:35:41",
        "endTime": "2023-07-30T12:35:41"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.6.3 管理员获取所有折扣信息

- get请求
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/discount_info/discount_infos
```



- 响应结果

```json
{
    "result": [
        {
            "discountId": "1684813417543634945",
            "discountName": "会员折扣活动",
            "discountRate": 90,
            "operateAdminUid": "1642398369596944385",
            "number": 5,
            "lockNumber": 0,
            "isEnd": false,
            "isDelete": false,
            "startTime": "2023-07-29T11:35:41",
            "endTime": "2023-08-03T12:35:41",
            "createTime": "2023-07-28T14:29:56",
            "updateTime": "2023-07-31T11:17:07"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.6.4 管理员删除指定discountId折扣信息

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/discount_info/delete_discount_info
```



- 参数

| 参数名     | 参数含义 |
| ---------- | -------- |
| discountId | 折扣id   |



- 示例

```bash
$baseUrl/video/discount_info/delete_discount_info?discountId=1690985946784247809
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.7 VideoCollectionController

##### 2.7.1 添加影视收藏

- put请求，url传参
- 需要登录状态

```bash
$baseUrl/video/video_collection/new_collection
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| videoInfoId | 影视信息id |



- 示例

```bash
$baseUrl/video/video_collection/new_collection?videoInfoId=1683373796746121218
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.7.2 删除指定collectionId影视收藏

- delete请求，url传参
- 需要登录状态

```bash
$baseUrl/video/video_collection/delete_collection
```



- 参数

| 参数名       | 参数含义 |
| ------------ | -------- |
| collectionId | 收藏id   |



- 示例

```bash
$baseUrl/video/video_collection/delete_collection?collectionId=1688452144841166850
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.7.3 分页查询用户自身收藏信息

- post请求，请求体传参
- 需要登录状态

```bash
$baseUrl/video/video_collection/user_self_collection_infos
```



- 参数

| 参数名      | 参数含义         |
| ----------- | ---------------- |
| queryWords  | 查询关键字，可选 |
| currentPage | 当前页数         |
| limited     | 每页数量         |



- 示例

```json
{
    "queryWords": "鬼灭之刃 锻刀村篇",
    "currentPage": 1,
    "limited": 8
}
```



- 响应结果

```json
{
    "result": {
        "records": [
            {
                "collectionId": "1688453333381070849",
                "uid": "1642067605873348610",
                "videoInfoId": "1683373796746121218",
                "videoName": "鬼灭之刃 锻刀村篇",
                "videoDescription": "卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG",
                "createTime": "2023-08-07T15:33:39",
                "updateTime": "2023-08-07T15:33:39"
            }
        ],
        "total": 1,
        "size": 8,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.8 VideoWatchHistoryController

##### 2.8.1 获取用户观看记录

- get请求
- 需要登录状态

```bash
$baseUrl/video/video_watch_history/watch_history_infos
```



- 响应结果

```js
{
    "result": [
        {
            "videoInfoId": "1683373796746121218",
            "videoSeriesId": "1683373881982767105",
            "videoName": "鬼灭之刃 锻刀村篇",
            "videoDescription": "卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG",
            "seriesNumber": 5
        },
        {
            "videoInfoId": "1682551371754180610",
            "videoSeriesId": "1682564048132014082",
            "videoName": "花神之舞",
            "videoDescription": "妮露 花神之舞",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/62286bc7-6c92-4dc1-80e4-71c2fefca495_捕获.PNG",
            "seriesNumber": 1
        },
        {
            "videoInfoId": "1683373796746121218",
            "videoSeriesId": "1683374990189187074",
            "videoName": "鬼灭之刃 锻刀村篇",
            "videoDescription": "卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。",
            "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG",
            "seriesNumber": 6
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 2.9 RollVideoController

##### 2.9.1 管理员添加轮播图信息

- put请求，form-data上传文件
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/roll_video/new_roll_video
```



- 参数

| 参数名          | 参数含义                       |
| --------------- | ------------------------------ |
| file            | 文件                           |
| rollDescription | 轮播图描述                     |
| videoInfoId     | 影视信息id                     |
| sort            | 排序字段，越小越靠前，范围0-15 |



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.9.2 获取轮播图信息

- get请求

```bash
$baseUrl/video/roll_video/roll_videos
```



- 响应结果

```json
{
    "result": [
        {
            "rollId": 2,
            "rollDescription": "koori",
            "videoInfoId": "1683373796746121218",
            "rollImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-12/6f994036-cc09-4bf2-8114-96e9c43d7401_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
            "sort": 0,
            "createTime": "2023-08-12T10:31:05",
            "updateTime": "2023-08-12T10:31:05"
        },
        {
            "rollId": 3,
            "rollDescription": "koori",
            "videoInfoId": "1683373796746121218",
            "rollImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-12/63f54179-c91e-4970-9356-7bc40a4a6345_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
            "sort": 1,
            "createTime": "2023-08-12T10:31:11",
            "updateTime": "2023-08-12T10:31:11"
        },
        {
            "rollId": 4,
            "rollDescription": "koori",
            "videoInfoId": "1683373796746121218",
            "rollImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-12/3660bd24-0fe3-47b6-9987-949432c72117_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
            "sort": 2,
            "createTime": "2023-08-12T10:31:21",
            "updateTime": "2023-08-12T10:31:21"
        }
    ],
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.9.3 管理员删除指定rollId轮播图信息

- delete请求，url传参
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/roll_video/delete_roll_video
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| rollId | 轮播图id |



- 示例

```bash
$baseUrl/video/roll_video/delete_roll_video?rollId=4
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 2.9.4 管理员修改指定rollId轮播图信息

- post请求，form-data上传文件
- 需要登录状态
- 需要admin角色

```bash
$baseUrl/video/roll_video/update_roll_video
```



- 参数

| 参数名          | 参数含义                             |
| --------------- | ------------------------------------ |
| file            | 文件，可选                           |
| rollId          | 轮播图id                             |
| rollDescription | 轮播图描述，可选                     |
| sort            | 排序字段，越小越靠前，范围0-15，可选 |



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



### 3 third_party第三方微服务

#### 3.1 MailController

##### 3.1.1 发送注册邮件

- post请求
- 建议只提供给微服务远程调用

```bash
$baseUrl/third_party/mail/send_register_mail
```



- 参数

| 参数名   | 参数含义   |
| -------- | ---------- |
| receiver | 接收者邮件 |
| code     | 验证码     |



- 示例

```bash
$baseUrl/third_party/mail/send_register_mail?receiver=$receiver&code=$code
```



- 响应格式

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



#### 3.2 OOSController

##### 3.2.1 获取oss上传信息

- get请求
- 需要登录状态

```bash
$baseUrl/third_party/oss/policy
```



- 响应结果

```json
{
    "result": {
        "accessId": "LTAI5tBonVMHDDUwqkcCGzCr",
        "policy": "eyJleHBpcmF0aW9uIjoiMjAyMy0wNC0xMVQwNzowMDo1Mi42NDJaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCJuZWtvL25la29fY29udmVuaWVudC8yMDIzLTA0LTExLyJdXX0=",
        "signature": "DyJi2r4mXLyHM+8FnBZxnTJbxTw=",
        "dir": "neko/neko_convenient/2023-04-11/",
        "host": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com",
        "expire": 1681196452,
        "callbackUrl": "https://192.168.30.1:8004"
    },
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 3.2.2 oss图片上传

- post请求，form-data上传文件
- 建议只提供给微服务远程调用

```bash
$baseUrl/third_party/oss/upload_image
```



- 参数

| 参数名 | 参数含义 |
| ------ | -------- |
| file   | 文件     |



- 响应结果

```json
{
    "result": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_convenient/2023-06-23/7ef5ba15-8c56-4c07-8c94-8ce75d40d1bb.png",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 3.2.3 删除oss文件

- delete请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/third_party/oss/delete_file
```



- 参数

| 参数名      | 参数含义   |
| ----------- | ---------- |
| ossFilePath | oss文件url |



- 示例

```bash
$baseUrl/third_party/oss/delete_file?ossFilePath=https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_convenient/2023-06-23/bd583144-8681-47e0-8113-50dfced4e9da.png
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 3.2.4 oss视频上传

- post请求，form-data上传文件
- 建议只提供给微服务远程调用

```bash
$baseUrl/third_party/oss/upload_video
```



- 响应结果

```json
{
    "result": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-20/7ffcdf19-4d4c-4440-b454-2bce2735ed24_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.mp4",
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```



##### 3.2.5 批量删除oss文件

- delete请求，url传参
- 建议只提供给微服务远程调用

```bash
$baseUrl/third_party/oss/delete_file_batch
```



- 参数

| 参数名       | 参数含义         |
| ------------ | ---------------- |
| ossFilePaths | 数组，oss文件url |



- 示例

```json
[
    "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-09/1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
    "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-09/1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795_bak.jpeg"
]
```



- 响应结果

```json
{
    "result": null,
    "responseStatus": "SUCCESS",
    "responseCode": 200,
    "responseMessage": "ok"
}
```


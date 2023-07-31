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
            "courier_read",
            "courier_write",
            "market_read",
            "market_write",
            "基本视频观看"
        ],
        "roleTypes": [
            "courier",
            "market",
            "user",
            "普通会员"
        ],
        "memberLevelRoleType": "普通会员",
        "token": "d393fdb3-3441-46a2-af15-04533b1b89bd",
        "gender": null,
        "source": null,
        "sourceUid": null,
        "realName": "NEKO",
        "idCardNumber": "420881200101184053",
        "phone": null,
        "mail": "NEKO@NEKO.com",
        "level": null,
        "point": null,
        "isBan": false,
        "isDelete": false,
        "createTime": "2023-04-01T15:33:20",
        "updateTime": "2023-07-25T17:12:07"
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

##### 2.1.1 获取层级商品分类信息

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
                "videoInfoId": "1680783199812259841",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/68085315-f614-42de-ba03-329b09a95762_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:17",
                "updateTime": "2023-07-17T11:35:17"
            },
            {
                "videoInfoId": "1680783216799191041",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/4614a8fc-6307-48c0-b250-5726358041a5_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:21",
                "updateTime": "2023-07-17T11:35:21"
            },
            {
                "videoInfoId": "1680783233358303233",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/40f59830-6945-4c48-8cec-5bc5433364d5_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:25",
                "updateTime": "2023-07-17T11:35:25"
            },
            {
                "videoInfoId": "1680783251037294593",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/463e007e-74a6-43c2-ab7a-37345a2bfe8d_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:29",
                "updateTime": "2023-07-17T11:35:29"
            },
            {
                "videoInfoId": "1680783299959656449",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/260b5481-b78e-4ea9-92ec-5bb88d0d3842_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:41",
                "updateTime": "2023-07-17T11:35:41"
            },
            {
                "videoInfoId": "1680783320826318849",
                "videoName": "NEKO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/259da5b9-f07d-4d0e-ad94-f0cae0a64762_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "categoryId": 34,
                "upTime": "2023-03-29T15:46:35",
                "status": -1,
                "createTime": "2023-07-17T11:35:46",
                "updateTime": "2023-07-17T11:35:46"
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



##### 2.2.4 管理员根据videoInfoId查询影视视频信息

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
                "videoInfoId": "1680781825485639682",
                "videoName": "NEO",
                "videoDescription": "NEKO",
                "videoImage": "https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg",
                "categoryId": 34,
                "categoryName": "校园",
                "videoProducer": "NEKO",
                "videoActors": "NEKO",
                "upTime": "2023-07-17 11:29:49"
            }
        ],
        "total": 1,
        "size": 8,
        "current": 1
    },
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



#### 2.5 OrderInfoController

##### 2.5.1 获取预生成订单token，保证预生成订单接口幂等性

- get请求
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



##### 2.6.1 提交订单

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



##### 2.6.3 根据订单号获取支付宝支付页面

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


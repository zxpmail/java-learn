# windows系统部署

## 1 下载安装包

```
https://dl.minio.io/server/minio/release/windows-amd64/minio.exe
```

### 2 启动MinIO

第一步，找到minio.exe所在的目录，在地址栏输入cmd进入cmd窗口

第二步，输入minio server d:\minio\data命令启动MinIO。 d:\minio\data为MinIO上传文件保存目录，可自定义保存目录。

```shell
D:\minio>minio server d:\minio\data
MinIO Object Storage Server
Copyright: 2015-2022 MinIO, Inc.
License: GNU AGPLv3 <https://www.gnu.org/licenses/agpl-3.0.html>
Version: RELEASE.2022-08-26T19-53-15Z (go1.18.5 windows/amd64)

Status:         1 Online, 0 Offline.
API: http://192.168.2.192:9000  http://192.168.132.1:9000  http://192.168.75.1:9000  http://127.0.0.1:9000
RootUser: minioadmin
RootPass: minioadmin
Console: http://192.168.2.192:14458 http://192.168.132.1:14458 http://192.168.75.1:14458 http://127.0.0.1:14458
RootUser: minioadmin
RootPass: minioadmin

Command-line: https://docs.min.io/docs/minio-client-quickstart-guide
   $ mc.exe alias set myminio http://192.168.2.192:9000 minioadmin minioadmin

Documentation: https://docs.min.io
```

### 3 登录MinIO后台

打开http://localhost:9000地址，输入Username 和 password即可登录minio的管理界面了。

![image-20220901153559558](E:\java-learn\minio\img\image-20220901153559558.png)

![image-20220901153726800](E:\java-learn\minio\img\image-20220901153726800.png)

### 4 修改密码

第一步，找到minio.exe所在目录，进入cmd窗口。

第二步，输入set MINIO_ROOT_USER=admin命令，修改Username。

第三步，输入set MINIO_ROOT_PASSWORD=12345678命令，修改 password。

第四步，输入minio server d:\minio\data，启动minio。

### 5 添加服务

需要借助"Windows Service Wrapper"
小工具，下载地址： http://repo.jenkins-ci.org/releases/com/sun/winsw/winsw/1.18/winsw-1.18-bin.exe

把winsw-1.18-bin.exe改名为minio-service.exe

新建 minio-service.xml 内容如下

```xml
<service>
	<id>minio</id>
	<name>MinIO Service</name>
	<description>MinIO is a High Performance Object Storage</description>
	<logpath>D:\minio\logs</logpath>
	<log mode="roll-by-size">
		<sizeThreshold>10240</sizeThreshold>
		<keepFiles>8</keepFiles>
	</log>
	<executable>D:\minio\run.bat</executable>
</service>
```

```shell
set MINIO_ROOT_USER=admin
set MINIO_ROOT_PASSWORD=12345678
minio server d:\minio\data
```

带密码带自己定义端口9999启动命令：

```shell
set MINIO_ROOT_USER=admin
set MINIO_ROOT_PASSWORD=12345678
minio.exe server --address :9999 d:\minio\data
```

用来把minio.exe当成服务开机启动,以管理员身份运行cmd，使用安装服务命令如下：

安装服务 minio-service.exe install(cmd下执行这行)
卸载服务 minio-service.exe uninstall

进入系统服务，启动服务即可

![image-20220901160150775](E:\java-learn\minio\img\image-20220901160150775.png)

[MinIO学习 - 有梦想的鱼i - 博客园 (cnblogs.com)](https://www.cnblogs.com/yuxl01/archive/2022/05/16/16226701.html)

[高性能分布式对象存储——MinIO（环境部署） - 大数据老司机 - 博客园 (cnblogs.com)](https://www.cnblogs.com/liugp/p/16558869.html)

https://mp.weixin.qq.com/s/e1x_cA8JdFklHxqqVfe0eQ
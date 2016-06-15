# 编程培训 & 第一题和第二题

## 题目

### 第一题

编程实现IP，TCP，UDP报文格式。实现报文的添加头部等基本操作。并实现IP报文的分段和重组操作。

* 程序输入：模拟一个应用层程序向你提交任一一段字符串，以及一个给定的传输层协议

* 程序输出：已经完成封装的三层IP报文，打印或写入文件

* 主要知识点：涉及一些基本的字符串操作、基本的数据结构使用（c、c++）或类的使用（JAVA、c#）、IP TCP UDP协议

* 因为协议的实现一般比较靠近底层，这道题推荐使用c、c++/Linux实现，实现过程中请善用数据结构、报文的层层封装过程应用函数封装好

* 根据以太网的MTU值完成IP报文对上层数据的分段，分段过程请严格按照IP协议规定进行分段，分段得到的报文要能在第二题中得到重组

* 关于协议的参考资料：TCP/IP协议族、TCP/IP详解卷1 协议

### 第二题

已有一个从网络上抓取的报文，保存在文件中。编程实现读取文件中的内容。从文件的二进制流中确认网络报文。并解析

* 第一题的逆过程，这里不再详细阐述，同样推荐使用c、c++/Linux实现

* 程序输入：一个或多个网络中抓取的报文，从文件中读入

* 程序输出：解析过程、报文每层使用的协议、还原高层数据，打印出来

* 测试数据可以从wireshark实际抓包获得，也可以使用第一题生成的字符串

* 需要注意，第一题封装到IP报文就完了，而第二题中抓取的报文一般是从以太帧头开始的，如果需要使用第一题生成的数据进行测试，可以选择把第一题扩展下封装到以太帧，或是把第二题抓包的报文限制在第三层IP报文

* 参考资料：wireshark软件，整道题其实就是在模拟wireshark软件的分析过程

## 概述

* 在main函数中进行字符串发送和接收

* 第一题的功能在第二题代码中实现了，__只需看第二题的代码即可__

* 由于上一个原因，第二题代码写了注释，第一题的代码就略过了

* 用单例实现了应用层、传输层、网络层和数据链路层的管理类

* 使用静态工厂模式创建每一个报文以免造成报文成员变量混乱

* 待发送数据来自于文件“stringInput”，生成的十六进制文件存于“rowData”， 最终解析出来的字符串通过标准输出流输出

## 设计与实现

按以下步骤设计

##### 1. 设计报文传输的总流程

##### 2. 实现每一层的抽象接口及功能

* IApplicationPresenter

```java
    void sendMessageToTransportLayer(String message, SimpleProtocolType simpleProtocolType);
    void sendMessageToUser(String message);
    void getMessageFromUser(String message);
    void getMessageFromUser(String message, SimpleProtocolType simpleProtocolType);
    void getMessageFromTransport(String message);
```

* ITransportPresenter

```java
	boolean getMessageFromApplication(String data, SimpleProtocolType simpleProtocolType);
    boolean getMessageFromNetwork(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean sendMessageToApplication(String data);
    boolean sendMessageToNetwork(byte[] data, SimpleProtocolType simpleProtocolType);
```

* INetworkPresenter

```java
	boolean getMessageFromTransport(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean getMessageFromDataLink(byte[] data);
    boolean sendMessageToTransport(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean sendMessageToDataLink(byte[] data);
```

* IDataLinkPresenter

```java
	boolean getMessageFromNetwork(byte[] data);
    boolean getMessageFromPHY(byte[] data);
    boolean sendMessageToNetwork(byte[] data);
    boolean sendMessageToPHY(byte[] data);
```

##### 3. 各层实例类实现接口并单例化

```java
	private ApplicationPresenter(){}

    public static ApplicationPresenter getInstance(){
        return ApplicationPresenterInstance.APPLICATION_PRESENTER;
    }

    private static class ApplicationPresenterInstance {
        private static final ApplicationPresenter APPLICATION_PRESENTER = new ApplicationPresenter();
    }
```

##### 4. 设计各层报文的javabean

* BaseJavaBean

* TCPMessage

* UDPMessage

* IPv4Pakcet

* EthernetFrame

##### 5. 静态工厂模式实现各javabean

```java
	private IPv4Packet() {
    }

	public static class Builder {
        IPv4Packet iPv4Packet = new IPv4Packet();

        public Builder setVirsion(byte virsion) {
            iPv4Packet.virsion = virsion;
            return this;
        }

        public Builder setHLEN(byte HLEN) {
            iPv4Packet.HLEN = HLEN;
            return this;
        }

        public Builder setDS(byte DS) {
            iPv4Packet.DS = DS;
            return this;
        }

        public Builder setTotalLength(short totalLength) {
            iPv4Packet.totalLength = totalLength;
            return this;
        }

        public Builder setIdentification(short identification) {
            iPv4Packet.identification = identification;
            return this;
        }

        public Builder setFlags(byte flags) {
            iPv4Packet.Flags = flags;
            return this;
        }

        public Builder setFragmentOffset(short fragmentOffset) {
            iPv4Packet.fragmentOffset = fragmentOffset;
            return this;
        }

        public Builder setTtl(byte ttl) {
            iPv4Packet.ttl = ttl;
            return this;
        }

        public Builder setProtocol(byte protocol) {
            iPv4Packet.protocol = protocol;
            return this;
        }

        public Builder setHeaderCheckSum(short headerCheckSum) {
            iPv4Packet.headerCheckSum = headerCheckSum;
            return this;
        }

        public Builder setSourceAddress(int sourceAddress) {
            iPv4Packet.sourceAddress = sourceAddress;
            return this;
        }

        public Builder setDestinationAddress(int destinationAddress) {
            iPv4Packet.destinationAddress = destinationAddress;
            return this;
        }

        public Builder setOptions(byte[] options) {
            iPv4Packet.options = options;
            return this;
        }

        public Builder setTransprotData(byte[] transprotData) {
            iPv4Packet.transprotData = transprotData;
            return this;
        }

        public IPv4Packet build() {
            return iPv4Packet;
        }
    }
```

##### 实现协议类型的枚举，统一程序描述

```java
	public enum SimpleProtocolType {
        TCP,
        UDP,
	}
```

##### 实现相关参数工具类，避免硬编码

```java
	public class RelativeParameterUtil {
    	public static final short SOURCEPROT = (short) 0x8000;
    	public static final short DESTINATIONPROT = (short) 0x8080;

	    public static final byte DATAOFFSET = 0x5;
	    public static final byte IPVIRSION = 4;

    	public static final byte DESTINATIONMACADDRESS = 0;
    	public static final byte SOURCEMACADDRESS = 0;

    	public static final int SOURCEIPADDRESS = 192 * 256 * 256 * 256 + 168 * 256 * 256 + 1 * 256 + 1 * 1;
    	public static final int DESTINATIONIPADDRESS = 192 * 256 * 256 * 256 + 168 * 256 * 256 + 1 * 256 + 2 * 1;

    	public static final String ROWDATAFILENAME = "rawData";
    	public static final String STRINGINPUTFILENAME = "stringInput";

    	public static final int TCPPROTOCOLNUM = 6;
    	public static final int UDPPROTOCOLNUM = 17;

    	public static final int MAXSIZEOFTCPMESSAGE = 1400;
    	public static final int MAXSIZEOFIPPACKET = 1400;
	}
```

##### 实现数据操作的工具类

```java
	public static String byteArray2String(byte[] data)
    public static void printStringFromByteAray(byte[] bytes)
    public static void writeIntoFile(String fileName, String resultData, boolean isBegin)
    public static List<byte[]> splitByteArray(byte[] targetData, int MAXLEN)
    public static byte[] spliceByte(byte[] origin, byte[] target)
```

## 程序运行流程

* main函数调用应用层presenter发出字符串（和传输层协议类型）

* 应用层presenter不加操作地传给传输层presenter

* 传输层presenter确定协议类型并组装TCP或UCP报文，选择性分组后传给网络层presenter

* 网络层presenter选择性分组后传给数据链路层presenter

* 数据链路层presenter不加操作地存入指定文件中

* main函数调用数据链路层presenter从指定文件读取数据

* 依次向上递交并解封装

* 最终在应用层presenter输出结果

## 遇到的问题

* 字符串转byte和byte转字符串 __已解决__

* TCP分组如何重组 __已解决__

* TCP虚报头checksum计算
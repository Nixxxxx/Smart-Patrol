<%@ page contentType="image/jpeg"
	import="java.awt.*,
java.awt.image.*,java.util.*,javax.imageio.*"
	pageEncoding="utf-8"%>
<%!
	Color getRandColor(int fc,int bc){//给定范围获得随机颜色 
	Random random =new Random();
	 if(fc>255)
		fc=255;
	 if(bc>255)
	 	bc=255;
	 int r=fc+random.nextInt(bc-fc);  //random.nextInt(bc-fc) 随机产生一个0--bc-fc的值
	 int g=fc+random.nextInt(bc-fc);
	 int b=fc+random.nextInt(bc-fc);
	 return new Color(r,g,b);
	}
%>
<%
	out.clear(); //针对resin服务器  tocmat 可以不需要 
	response.setHeader("Pragma","No-cache");//response设置页面不缓存
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires",0);
	int width=60,height=20;  //图片的宽度高度
	BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);// 图片的内存映像  --在内存中创建图像
	Graphics g=image.getGraphics();    // 获得画笔对象
	Random random=new Random(); //获取随机类
	g.setColor(getRandColor(200,250));  // 设定背景色  
	g.fillRect(0, 0, width, height);// 画一个实心的长方  作为背景
	g.setFont(new Font("Times New Roman",Font.PLAIN,18));//设定字体 
	g.setColor(getRandColor(160,200)); // 设定背景色
	for(int i=0;i<155;i++){          // 绘制干扰线， 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		int x=random.nextInt(width);
		int y=random.nextInt(height);
		int x1=random.nextInt(12);
		int y1=random.nextInt(12);
		g.drawLine(x, y, x+x1, y+y1);
	}
	String sRand="";  // 用于存储随机生成的验证码
	for(int i=0;i<4;i++){   // 绘制验证码   取随机产生的认证码(4位数字) 
		String[] rand={"0","1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
		int length=random.nextInt(rand.length);
		sRand+=rand[length];
		g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));// 将认证码显示到图象中 
		g.drawString(rand[length], 13*i+6, 16);//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成  
	}
	//将认证码存入session 
	session.setAttribute("sRand",sRand);
	g.dispose();// 图象生效  
	ImageIO.write(image,"JPEG", response.getOutputStream());// 输出图象到页面 
%>
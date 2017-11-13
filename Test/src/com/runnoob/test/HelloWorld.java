package com.runnoob.test;

import java.io.BufferedReader;  
import java.io.Writer;
import java.io.FileWriter;
import java.io.File;   
import java.io.FileReader;  
import java.io.IOException;  
import java.util.Scanner;
import java.util.*;
import com.runnoob.test.GraphViz;


//�ڽӾ���ͼ
class ListDG {

	private static int INF = Integer.MAX_VALUE;

	//����Ķ���
	private class ENode 
	{
		//�ñ���ָ��Ķ����λ��
		int ivex;
		//ָ����һ������ָ��
		ENode nextEdge;
		//Ȩ��
		int weight;
	}
	
	//��Ķ���
	private class VNode
	{
		//������Ϣ
		String data;
		//ָ���һ�������ö���Ļ�
		ENode firstEdge;
		//�õ��м���·��������
		int k=0;
	}

	//��������
	public VNode[] mVexs;
	
	//����ͼ
	public ListDG(String[] vexs, String[][] edges)
	{



		//��ʼ���������ͱ���
		int vlen = vexs.length;
		int elen = edges.length;
		
		//��ʼ������
		mVexs = new VNode[vlen];
		for (int i = 0; i < mVexs.length; i++)
		{
			mVexs[i] = new VNode();
	        mVexs[i].data = vexs[i];
	        mVexs[i].firstEdge = null;
		}
		
		//��ʼ����
		for (int i = 0; i < elen; i++) 
		{
	        // ��ȡ�ߵ���ʼ����ͽ�������
	        String c1 = edges[i][0];
	        String c2 = edges[i][1];
	        // ��ȡ�ߵ���ʼ����ͽ�������
	        int p1 = getPosition(edges[i][0]);
	        int p2 = getPosition(edges[i][1]);

	        ENode node2 = mVexs[p1].firstEdge;
	        int flag = 0;
	        while(node2 != null) {
	        	if(node2.ivex == p2) {
	        		flag = 1;
	        		node2.weight ++;
	        	}
	        	node2 = node2.nextEdge;
	        }
	        if(flag == 0) {
	        	// ��ʼ��node1
	        	ENode node1 = new ENode();
		        node1.ivex = p2;
		        node1.weight = 1;
		        node1.nextEdge = null;
	        	if(mVexs[p1].firstEdge == null)
	            mVexs[p1].firstEdge = node1;
	        else
	            linkLast(mVexs[p1].firstEdge, node1);
	        }
	        // ��node1���ӵ�"p1���������ĩβ"
	     }
		
		//��ʼ����ֵk
		for (int i = 0; i < mVexs.length; i++) 
		{
		    ENode node = mVexs[i].firstEdge;
		    while (node != null) 
		    {
		        mVexs[i].k+=1;
		        node = node.nextEdge;
		    }
		}
	}

	//��ȡ��<start, end>��Ȩֵ����start��end������ͨ�ģ��򷵻������
	private int getWeight(int start, int end)
	{
		if (start==end)
			return 0;

		ENode node = mVexs[start].firstEdge;
		while (node!=null) {
			if (end==node.ivex)
				return node.weight;
			node = node.nextEdge;
		}

		return INF;
	}
		
	//��node�ڵ����ӵ�list�����
	private void linkLast(ENode list, ENode node) 
	{
		ENode p = list;
		while(p.nextEdge!=null)
			p = p.nextEdge;
		p.nextEdge = node;
	}
	
	//����ch��λ��
	private int getPosition(String ch) 
	{
		for(int i=0; i<mVexs.length; i++)
		{
			if(ch.equals(mVexs[i].data))
			{
				return i;
			}
		}
		return -1;
	}


	/*public static void shortpath(int p1, int p2)
	{
		//String color = "[color = red]";
		while( p2 != p1 ){
			//p2 = p1;
			for(int i = 0; i < vlen ; i++){
				if(path[p2][i] == 1){
					gv.changeAll(mVexs[i].data + "->" + mVexs[p2].data, mVexs[i].data + "->" + mVexs[p2].data + color);
					p2 = i;
					//shortpath = shortpath + " " + mVexs[p2].data;
					break;
				}
			}
		}
	}*/


	public void getpath(int[][] path,int start,int end)
	{
		GraphViz gv = new GraphViz();
		
		if(gv.empty() == 0)
    	{
    		gv.addln(gv.start_graph());
    		int vlen = mVexs.length;
    		for(int i = 0; i < vlen ; i++)
    		{
    			ENode node = mVexs[i].firstEdge;
    			while(node != null)
    			{
    				String edge = mVexs[i].data + "->" + mVexs[node.ivex].data + " [label = " + node.weight + "]";
    				gv.addln(edge);
    				node = node.nextEdge;
    			}
    		}
    		gv.addln(gv.end_graph());
    	}
		
		String color = "[color = red]";
		while( start != path[start][end] ){
			//p2 = p1;
			
					gv.changeAll(mVexs[start].data + "->" + mVexs[path[start][end]].data, mVexs[start].data + "->" + mVexs[path[start][end]].data + color);
					
					//shortpath = shortpath + " " + mVexs[p2].data;
					//System.out.printf("%s  %s \n",mVexs[path[start][end]].data, mVexs[end].data);
					start = path[start][end];
			
		}
		gv.increaseDpi();   // 106 dpi
    	String type = "jpg";
    	String repesentationType= "dot";

    	File out = new File("E:/xxx." + type);    // Windows
    	gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
	}

	public void calcShortestPath(String word1, String word2)
	{
		int w1=INF,w2=INF;
		for (int i = 0; i < mVexs.length; i++)
		{
			if (mVexs[i].data.equals(word1))
				w1=i;
			if (mVexs[i].data.equals(word2))
				w2=i;
		}

		int[][] path = new int[mVexs.length][mVexs.length];
		int[][] dist = new int[mVexs.length][mVexs.length];

		// ��ʼ��
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = 0; j < mVexs.length; j++) {
				dist[i][j] = getWeight(i, j);  // "����i"��"����j"��·������Ϊ"i��j��Ȩֵ"��
				path[i][j] = j;                // "����i"��"����j"�����·���Ǿ�������j��
			}
		}

		/*System.out.println("***");
		for(int[]i:dist){
			for(int j:i){
				System.out.print(j);
			}
			System.out.println();
		}*/


		// �������·��
		for (int k = 0; k < mVexs.length; k++) {
			for (int i = 0; i < mVexs.length; i++) {
				for (int j = 0; j < mVexs.length; j++) {

					// ��������±�Ϊk����·����ԭ�����·�����̣������dist[i][j]��path[i][j]
					int tmp = (dist[i][k]==INF || dist[k][j]==INF) ? INF : (dist[i][k] + dist[k][j]);
					if (dist[i][j] > tmp) {
						// "i��j���·��"��Ӧ��ֵ�裬Ϊ��С��һ��(������k)
						dist[i][j] = tmp;
						// "i��j���·��"��Ӧ��·��������k
						path[i][j] = path[i][k];
					}
				}
			}
		}

		int result=dist[w1][w2];

		// ��ӡfloyd���·���Ľ��
		System.out.printf("floyd: \n");
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = 0; j < mVexs.length; j++)
				System.out.printf("%10d  ", dist[i][j]);
			System.out.printf("\n");
		}

		//System.out.println("���·��Ϊ��");
		//System.out.print("���·������Ϊ��");



		if (result==0)
		{
			System.out.println("�õ㵽����������·��Ϊ��");
			for (int i=1;i<mVexs.length;i++)
			{
				System.out.print(mVexs[i].data+":");
				System.out.println(dist[w1][i]);
				
			}
		}
		else if (result==INF)
		{
			System.out.println("���ɴ");
		}
		else
		{
			System.out.print("���·������Ϊ��");
			System.out.println(result);
			getpath(path,w1,w2);
			
		}

		System.out.println();

		/*System.out.printf("floyd: \n");
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = 0; j < mVexs.length; j++)
				System.out.printf("%10d  ", path[i][j]);
			System.out.printf("\n");
		}*/

	}
		 
	// ��ӡ�������ͼ
	public void print() 
	{
		System.out.printf("List Graph:\n");
		for (int i = 0; i < mVexs.length; i++) 
		{
		    System.out.printf("%d(%s %d): ", i, mVexs[i].data,mVexs[i].k);
		    ENode node = mVexs[i].firstEdge;
		    while (node != null) 
		    {
		        System.out.printf("%d(%s %d) ", node.ivex, mVexs[node.ivex].data,node.weight);
		        node = node.nextEdge;
		    }
		    System.out.printf("\n");
		}
		System.out.printf("\n\n\n\n");
	}
	
	//չʾ����ͼ�ĺ���
    public void showDirectedGraph()
    {
    	//��Ļ�������ͼ
    	print();
    	//ͼ����ʽ����������
    	GraphViz gv = new GraphViz();
    	if(gv.empty() == 0)
    	{
    		gv.addln(gv.start_graph());
    		int vlen = mVexs.length;
    		for(int i = 0; i < vlen ; i++)
    		{
    			ENode node = mVexs[i].firstEdge;
    			while(node != null)
    			{
    				String edge = mVexs[i].data + "->" + mVexs[node.ivex].data + " [label = " + node.weight + "]";
    				gv.addln(edge);
    				node = node.nextEdge;
    			}
    		}
    		gv.addln(gv.end_graph());
    	}

    	gv.increaseDpi();   // 106 dpi
    	String type = "jpg";
    	String repesentationType= "dot";

    	File out = new File("E:/out." + type);    // Windows
    	gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );

    }
	
    //��ѯ�ŽӴ�
    public String queryBridgeWords(String word1, String word2)
    {
    	//re1����������ķ���ֵ
    	String re1="";
    	//flag3��¼���ҵ������ŽӴ�
    	int flag3=0;
    	
    	for (int i = 0; i < mVexs.length; i++) 
		{
    		//�ҵ�word1
		    if( (mVexs[i].data).equals(word1) )
		    {
		    	ENode node = mVexs[i].firstEdge;
			    while (node != null) 
			    {
			    	//node2.data ���ǿ��ܵ��ŽӴ�
			    	VNode node2 = mVexs[node.ivex];
			    	ENode node3 = node2.firstEdge;
			    	while(node3 != null)
			    	{
			    		if( ( mVexs[node3.ivex].data ).equals(word2) )
			    		{
			    			re1+=node2.data;
			    			re1+="  ";
			    			flag3+=1;
			    			break; //�����break����һ��ѭ��
			    		}
			    		node3 = node3.nextEdge;
			    	}
			        node = node.nextEdge;
			    }
		    }
		  }
    	return re1;
    }
    
    //�����ŽӴ��������ı��ĺ���
  	//����Ĳ����ǣ����ı����ڽӱ��еĴ洢������ַ�������s4
  	public String generateNewText(String inputText)
  	{
  		String NewText = "";
  		//inputText����ַ�������sen1
          String[] sen1 = inputText.split("\\s{1,}");
          
          for(int i=0;i<sen1.length-1;i++)
          {
          	String word1 = sen1[i];
          	String word2 = sen1[i+1];
          	//���word1 word2�Ƿ����ŽӴ�
          	//����У����word1���ŽӴ�
          	//���û�У�ֻ���word1
          	
          	//�ж�word1 word2�Ƿ����
              int flag1=0;
              int flag2=0;
              for(int j=0;j<mVexs.length;j++)
              {
              	if(word1.equals(mVexs[j].data))
              	{
              		flag1+=1;
              		break;
              	}
              }
              for(int j=0;j<mVexs.length;j++)
              {
              	if(word2.equals(mVexs[j].data))
              	{
              		flag2+=1;
              		break;
              	}
              }
              
              //word1����word2��������ͼ�еĶ���
              //�����ŽӴ�
              if(flag1==0||flag2==0)
              {
              	NewText+=word1;
              	NewText+=" ";
              }
              //�������ŽӴ�
              else
              {
              	String re1=queryBridgeWords(word1,word2);
              	//���ŽӴ�
                  if(re1.equals(""))
                  {
                  	NewText+=word1;
                  	NewText+=" ";
                  }
                  //���ŽӴ�
                  else
                  {
                  	NewText+=word1;
                  	NewText+=" ";
                  	NewText+=re1;
                  	NewText+=" ";
                  }
              }
          }
          int k= sen1.length;
          NewText += sen1[k-1];
          return NewText;
  	}
    
  //�������
  	public String randomWalk()
  	{
  		String str = "";
  		
  		//������¼�������ı�
  		int visit[][] = new int[1000][1000];
  		//�����ĵ�һ����
  		int random1 = (int)(Math.random()*(mVexs.length));
  		str+=mVexs[random1].data;
  		str+=" ";
  		int flag = 1;
  		while(flag==1)
  		{
  			//���ö���������·��
  			if(mVexs[random1].k==0)
  			{
  				flag=0;
  			}
  			else
  			{
  				//���������һ����
  				int random2=(int)(Math.random()*(mVexs[random1].k));
  				ENode node;
  				node = mVexs[random1].firstEdge;
  				
  				if(visit[random1][node.ivex] == 1)
  				{
					str += mVexs[node.ivex].data;
					str += " ";
					flag = 0;
					continue;
				}
  				
				for(int i = 1;i < random2;i++)
				{
					node = node.nextEdge;
				}
				//��ʾ�ñ��Ѿ�������
				visit[random1][node.ivex] = 1;
				str += mVexs[node.ivex].data;
				str += " ";
				random1 = node.ivex;		
  			}
  		}
  		return str;
  	}
    
	
}


public class HelloWorld {
	
	public static void main(String []args) {

		//~~~~�����Ŀ��Ŀ¼�ľ���·��
		String path = System.getProperty("user.dir");
		System.out.print("�ļ�·����");
		System.out.println(path);

		//�����ļ�����λ�ã�
		System.out.println("Please input the position of the file:");
		Scanner sca = new Scanner(System.in);
		String FilePosition = null;
        FilePosition = sca.nextLine();
        //
    	File file = new File(FilePosition);

    	//~~~~�ɻ���ִ��
		//~~~~����File������һ��Scanner����
    	//�ַ���������
    	StringBuilder result = new StringBuilder();
    	//~~~~��ֹ�ļ����������쳣
    	try 
    	{
    		//����һ��BufferedReader������ȡ�ļ�
			//~~~~���뻺������Ϊ��Ч
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		String s= null;
    		//ʹ��readLineһ�ζ�һ��
    		while((s=br.readLine())!=null)
    		{
    			//����һ���ַ�����ĩβ
    			result.append(s);
    		}
    		br.close();
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	//~~~~��ɴ����ַ���
    	String s2= result.toString();
    	//System.out.println(s2);

    	//~~~~�ɻ�Ϊ��ȡʱ����
    	//������ʽ�ո���������ַ�����
		//~~~~�滻����ƥ�������������ʽ�����ַ���
    	s2=s2.replaceAll( "[\\p{P}+~$`^=|<>�����ޣ�������������0-9]"," "); 
    	s2 = s2.toLowerCase();
    	//~~~~s3�ַ�����������
    	//ɾ��һ�������ո��ַ�����Ϊ�ַ�������
    	//s3���������У����㡢�ߣ���Ϣ
    	String[] s3 = s2.split("\\s{1,}");

    	/*for (String s:s3)
    		System.out.println(s);*/
		
		//~~~~��s3ȥ��
    	//s4�������ظ��Ķ�����Ϣ
    	List<String> list = new LinkedList<String>();
    	for(int i=0;i<s3.length;i++)
    	{
    		if(!list.contains(s3[i]))
    		{
    			list.add(s3[i]);
    		}
    	}
    	String[] s4 = list.toArray(new String[list.size()]);
    	
    	//�����Ե����
        /*for(int i=0;i<s4.length;i++)
        {
        	System.out.print(s4[i]+" ");
        }
        System.out.println(" ");*/
        
        //����Ϣ
        String[][] s5 = new String[s3.length-1][2];
        for(int i=0;i<s3.length-1;i++)
        {
        	s5[i][0]=s3[i];
        	s5[i][1]=s3[i+1];
        }
        
        //�����ڽӱ�����ͼ
        ListDG pG = new ListDG(s4, s5);
        //չʾ�ڽӱ�����ͼ
        pG.showDirectedGraph();

//		int[][] path = new int[pG.mVexs.length][pG.mVexs.length];
//		int[][] floy = new int[pG.mVexs.length][pG.mVexs.length];

        System.out.println("Please choose the function you need :");
        System.out.println("1. queryBridgeWords ");
        System.out.println("2. generateNewText");
        System.out.println("3. calcShortestPath");
        System.out.println("4. randomWalk");
        Scanner sca1 = new Scanner(System.in);
		String choice = null;
        choice = sca1.next();
        if(choice.equals("1"))
        {
        	//�ж��Ƿ�����ù���
        	int Flag=1;
        	while(Flag==1)
        	{
        		//��ѯ�ŽӴ�
                //�û�������������Ӣ�ĵ���
                String word1 = null;
                String word2 = null;
                word1 = sca.next();
                word2 = sca.next();
                //�ж�word1 word2�Ƿ����
                int flag1=0;
                int flag2=0;
                for(int i=0;i<s4.length;i++)
                {
                	if(word1.equals(s4[i]))
                	{
                		flag1+=1;
                		break;
                	}
                }
                for(int i=0;i<s4.length;i++)
                {
                	if(word2.equals(s4[i]))
                	{
                		flag2+=1;
                		break;
                	}
                }
                
                if(flag1==0||flag2==0)
                {
                	System.out.println("No word1 or word2 in the graph!\r\n");
                }
                else
                {
                	String re1=pG.queryBridgeWords(word1,word2);
                	if(re1.equals(""))
                	{
                		System.out.printf("No bridge words from \"%s\" to \"%s\"!",word1,word2);
                	}
                	else
                	{
                		System.out.printf("The bridge words from \"%s\" to \"%s\" are: ",word1,word2);
                	}
                	System.out.println(re1);
                }
                System.out.println("************************");
                System.out.println("Do you want to continue?");
                Scanner sca3 = new Scanner(System.in);
        		String choice2 = null;
                choice2 = sca3.next();
                if(choice2.equals("NO"))
                {
                	Flag=0;
                	break;
                }
        	}
        }
        else if(choice.equals("2"))
        {
        	//�����ŽӴ��������ı�
            //�û�����һ�����ı�
            Scanner sca2 = new Scanner(System.in);
            StringBuffer input1 = new StringBuffer(sca2.nextLine());
            String inputText = input1.toString();
            String New = pG.generateNewText(inputText);
            String[] NewText  = New.split("\\s{1,}");
            //���������ʽ
            for(int i=0;i<NewText.length;i++)
            {
            	System.out.print(NewText[i]);
            	System.out.print(" ");
            }
        }
        else if(choice.equals("3"))
        {
        	System.out.println("���붥�㣺");
        	Scanner in1=new Scanner(System.in);
        	String w1=in1.next();
        	String w2=in1.next();
			pG.calcShortestPath(w1,w2);
        }
        else if(choice.equals("4"))
        {
        	//�������
            String str=pG.randomWalk();
            System.out.println(str);
            //�ļ����
            try
            {
            	Writer w = new FileWriter("E:/out.txt",true);
                w.write(str);
                w.close();
            } catch(IOException e)
            {
            	System.out.println("�ļ�д�����"+e.getMessage());
            }
            
        }
        else
        {
        	System.out.println("Nooooo....");
        }
    }
}
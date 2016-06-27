#Dagger2完全教程


----------

没有更多开场白，直接说下我对它的理解。

Dagger2 是一个Android依赖注入框架。而android开发当前非常流行的非MVP模式莫属了，Dagger2的目标便是将MVP中的V P 进一步解耦，达到模块化最大的解耦，使得代码更容易维护。

举个栗子：有个A对象 B对象 和C对象，如果C对象创建需要A和B，那么我们是不是需要构造里面传入参数A和参数B，然后在使用的地方如下写个代码：
```java
C c=new C(new A()，new B());
```
如果我们使用了Dagger2时候，我们就不需要管这些了，只需要关联住能提供创建A 和 B的地方 ，然后在需要C的地方写下：

```java
@Inject
C c;
```
然后在这个类的初始化地方进行注入即可。
我们初步来看，会发现Dagger2优势不大，没什么吸引人的，那么请你静下心来，看完再得出结论。

----------
闲话休叙，我们来直接上代码：（常规写法）

#1 编写一个类：
```java
public class Test3 {
    public Test3() {
    }
}
```
#2 使用的地方
```java
public class MainActivity extends AppCompatActivity {
    Test3 test3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //.....
        test3 = new Test3();
    }
}
```


----------

 - 如果我们改为使用Dagger2的方式的话，则可以写成如下方式：
#1 创建一个类
使用了注解方式，使得Dagger2能找到它。
```java
public class Test3 {
    //这里可以看到加入了注解方式
    @Inject
    public Test3() {
    }
}
```
#2 新增一个对象：

```java
@Singleton
//用这个标注标识是一个连接器
@Component()
public interface MainActivityComponent {
    //这个连接器要注入的对象。这个inject标注的意思是，我后面的参数对象里面有标注为@Inject的属性，这个标注的属性是需要这个连接器注入进来的。
    void inject(MainActivity activity);
}
```

#3 调用的地方改为：

```java
public class MainActivity extends AppCompatActivity {
    //加入注解，标注这个test3是需要注入的
    @Inject
    Test3 test3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用组件进行构造，注入
        DaggerMainActivityComponent.builder().build().inject(this);
    }
```
----------

这是最简单的一种使用了。首先我们看到，第一印象是我去，这个更复杂了啊。我只能说确实，因为这个是它对的最基础的使用，看起来很笨拙，但是当它在大型项目里面，在依赖更多的情况下，则会发生质的飞跃，会发现它非常好用，并且将你需要传递的参数都隐藏掉，来实现解耦。


----------


我先说下Dagger2的注释思路：关键的点是@Component，这个是个连接器，用来连接提供方和使用方的，所以它是桥梁。它使用在组件里面标记使用的Module（标记用到了哪个Module，主要是看使用方需要哪些对象进行构造，然后将它的提供方@module写在这里）	然后我们写入一个void inject(MainActivity activity); 这里后面的参数，就是我们的使用方了。如此一来，我们在使用的地方，使用类似这种方式（DaggerMainActivityComponent.builder().build().inject(this);）的动作，将使用方类里面的标记 为@Inject的类初始化掉，完成自动初始化的动作。

结构如下：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/1459515096_5841.png)


为了更好的来学习它，我们来依次看看各种使用情况。

#1 常规使用方法

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/1/1.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/1/2.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/1/3.png)


直接感受下，如何？

#2 带一个参数的效果

----------

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/2/1.png)

----------
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/2/2.png)

----------
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/2/3.png)

----------
我们来看一个代码段，当我们创建两个实例的时候，发现地址是独立的。

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/3/1.png)

如果我们想要一样的地址呢？加上一句话，具体如下：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/3/2.png)

效果便是两个共用实例啦。

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/3/3.png)


#3 换种经常使用的方式

将提供的构造，放入@module里面，具体效果如下：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/4/1.png)

去掉标记的@singleton后

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/4/2.png)

效果变成独立的啦

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/4/3.png)


#4 依赖一个组件的时候

有时我们需要依赖一个组件，这个最常见的用法是，我们App实例里面提供了比如获取sharepreference的实例，和比如现在代码里面的LocationManager的实例，我们Activity里面需要这些实例，我们该如何来做呢？看效果：
1：一个AndroidModule 模块标记

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/1.png)

这个模块属于AndroidcationComponent 组件里面

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/2.png)

这里有个关键点，就是子组件需要这个里面的某个实例的时候，这里需要使用一个接口，将需要的实例做一个返回动作。这里是LocationManager这一行。

我们的子组件的代码如下：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/3.png)

对应的Cmodule代码如下：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/4.png)

再来看下Test3的代码当前情况：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/5.png)

使用的地方：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/6.png)



----------

细心的你会发现这里多了一个注释了，@PerActivity，它是个什么鬼呢？

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/7.png)

这里我们看到它是使用了@Scope的一个注释，这个注释的意思就是作用域，在作用域内保持单例，可以直接理解为单例即可。
为什么要新增一个呢，主要是因为各个组件需要独立出来，因此如果是依赖关系，则需要各自在不同的注释作用域里面。
我们来看下在Cmodule里面，加上@perActivity注释后的效果：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/8.png)

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/9.png)

如果去掉呢？

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/10.png)


我们突然发现，它和单例的注释起的作用一样啊。so。。。是不是发现什么啦。

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/5/11.png)

因此我们得出一个结论，这里@Singleton
就是一个普通的作用域通道，使用了作用域@Scope注释的代码，会变成单例模式。为了验证我们的思路，作如下测试：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/6/1.png)

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/6/2.png)

我们将之前的@Singleton用新建的这个替换掉，验证两次的生成代码，发现一模一样，一模一样，一模一样，so。。。 就是这个样子啦。

#5 自定义一个标记
为什么要自定义标记呢？这个标记不是使用@Scope注释的哦，是使用@Qualifier 标记的，它的目标是，为了区分如果同时返回类型一样，比如构造男孩，女孩的基本属性，性别和名字时候，获取男孩和女孩都是一个对象，我们该如何区分呢，这个就是关键啦。说这么多，真心很烦，直接栗子来啦。

这里稍安勿躁，先来看相同效果的另一个注释，@Name，这个是Dagger2自带的一个让区分，效果如下：
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/1.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/2.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/3.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/4.png)

这里@Name可以简单的一个使用方式，就是它不是区分对象，而是限制使用时候必须加入这个注释，否则报错，目的就是让使用者注意是否使用正确了。

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/5.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/7/6.png)

我们使用自己的注释再来一遍：
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/8/1.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/8/2.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/8/3.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/8/4.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/8/5.png)

对比两种方式，我们发现使用@Name的时候，后面的注释名字会敲错，而我们第二种方式呢，则不会耶，so。。。

我们看下自定义的标记，作为限制出错，让强制标注的例子。
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/9/1.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/9/2.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/9/3.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/9/4.png)

#6 子组件（公共组件）
这个出现的目的是为了如果有一个组件，是每次创建实例提供给别人，而恰好其他组件（有多个）里面有需要它，如果只有一个，我们就用依赖搞定啦。那么它就可以定义成子组件，谁需要在谁的组件里面加一下，具体看例子：

![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/10/1.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/10/2.png)
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/10/3.png)


如上，写完啦。。

实战地方，可以参照https://github.com/gzsll/TLint来阅读啦，收工，需要交流，联系微信：code_gg_boy
更多精彩，时时关注微信公众号code_gg_home二维码长这样子啦：
![一个显示图](https://github.com/luxiaoming/dagger2Demo/raw/master/images/0.jpg)









	
	 

    

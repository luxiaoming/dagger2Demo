# dagger2Demo
删减了很多东西了，主要是做教程实时修改了

主要讲解实战dagger2的一些使用情况，作为基础功能的说明参照。

#Dagger2完全教程

        Dagger2 是一个Android依赖注入框架，由谷歌开发，最早的版本Dagger1 由Square公司开发。依赖注入框架主要用于模块间解耦，提高代码的健壮性和可维护性。Dagger 这个库的取名不仅仅来自它的本意“匕首”，同时也暗示了它的原理。Jake Wharton 在对 Dagger 的介绍中指出，Dagger 即 DAG-er，这里的 DAG 即数据结构中的 DAG——有向无环图(Directed Acyclic Graph)。也就是说，Dagger 是一个基于有向无环图结构的依赖注入库，因此Dagger的使用过程中不能出现循环依赖。
        Android开发从一开始的MVC框架，到MVP，到MVVM，不断变化。现在MVVM的data-binding还在实验阶段，传统的MVC框架Activity内部可能包含大量的代码，难以维护，现在主流的架构还是使用MVP（Model + View + Presenter）的方式。但是 MVP 框架也有可能在Presenter中集中大量的代码，引入DI框架Dagger2 可以实现 Presenter 与 Activity 之间的解耦，Presenter和其它业务逻辑之间的解耦，提高模块化和可维护性。

为了更好的了解Dagger2，我们先来看下一般情况下的使用：

编写一个类：

```java
public class Test3 {
    public Test3() {
    }
}
```
使用的地方
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
如果我们改为使用Dagger2的方式的话，则可以写成如下方式：
```java
public class Test3 {
    //这里可以看到加入了注解方式
    @Inject
    public Test3() {
    }
}
```
新增一个对象：
```java
@Singleton
//用这个标注标识是一个连接器
@Component()
public interface MainActivityComponent {
    //这个连接器要注入的对象。这个inject标注的意思是，我后面的参数对象里面有标注为@Inject的属性，这个标注的属性是需要这个连接器注入进来的。
    void inject(MainActivity activity);
}
```

调用的地方改为：
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
        这是最简单的一种使用了。首先我们看到，第一印象是我去，这个更复杂了啊。我只能说确实，因为这个是它对的最基础的使用，看起来很笨拙，但是当它在大型项目里面，在依赖更多的情况下，则会发生质的飞跃，会发现它非常好用，并且将你需要传递的参数都隐藏掉，来实现解耦。
        下面来看看Dagger2的基本内容。Dagger2 通过注解来生成代码，定义不同的角色，主要的注解有：@Inject、@Module 、@Component 、@Provides 、@Scope 、@SubComponent 等。
　　@Inject: 通常在需要依赖的地方使用这个注解。换句话说，你用它告诉Dagger2这个类或者字段需要依赖注入。这样，Dagger2就会构造一个这个类的实例并满足他们的依赖。
　　@Module: Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的 依赖。modules的一个重要特征是它们设计为分区并组合在一起（比如说，在我们的app中可以有多个组成在一起的modules）。
　　@Provides: 在modules中，我们定义的方法是用这个注解，以此来告诉Dagger2我们想要构造对象并提供这些依赖。
　　@Component: Components从根本上来说就是一个注入器，也可以说是@Inject和@Module的桥梁，它的主要作用就是连接这两个部分。 Components可以提供所有定义了的类型的实例，比如：我们必须用@Component注解一个接口然后列出所有的　　 @Modules组成该组件，如 果缺失了任何一块都会在编译的时候报错。所有的组件都可以通过它的modules知道依赖的范围。
　　@Scope: Scopes可是非常的有用，Dagger2可以通过自定义注解限定注解作用域。后面会演示一个例子，这是一个非常强大的特点，因为就如前面说的一样，没必要让每个对象都去了解如何管理他们的实例。
我们来看一个常规用法：
	我先说下Dagger2的注释思路：关键的点是@Component，这个是个连接器，用来连接提供方和使用方的，所以它是桥梁。它使用在组件里面标记使用的Module（标记用到了哪个Module，主要是看使用方需要哪些对象进行构造，然后将它的提供方@module写在这里）
	然后我们写入一个void inject(MainActivity activity); 这里后面的参数，就是我们的使用方了。如此一来，我们在使用的地方，使用类似这种方式（DaggerMainActivityComponent.builder().build().inject(this);）的动作，将使用方类里面的标记 为@Inject的类初始化掉，完成自动初始化的动作。

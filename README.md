# FireworkAnim [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)  [![RxTool](https://jitpack.io/v/vondear/RxTool.svg)](https://jitpack.io/#vondear/RxTool)  [![Twitter](https://img.shields.io/badge/Gradle-3.0.1-brightgreen.svg)](https://github.com/jiangzehui/polygonsview)

    由于公司项目开发需要一个点赞收藏的动效，想给用户一种新鲜感，不那么大众化的效果，于是就自己写了一个类似礼物抛洒的动效，
    本人无审美，所以不知道效果怎样，只是觉得跟一般的效果还是有区别的。如果有小伙伴觉得有用的话，欢迎使用！主要原理是利用
    贝塞尔曲线生成随机路径，然后加上一些辅助动画，看起来有一种抛洒的效果。

## 如何使用它

> Step 1.先在 build.gradle(Project:XXXX) 的 repositories 添加::

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
> Step 2. 然后在 build.gradle(Module:app) 的 dependencies 添加:

	dependencies {
	       implementation 'com.github.HeYongRui:FireworkAnim:v1.0.0'
	}

    使用方法:
         BezierFireworkAnim bezierFireworkAnim = new BezierFireworkAnim(activity);//此处传入Activity参数做上下文
         bezierFireworkAnim.startAnim(view);//传入动画的目标视图控件
         bezierFireworkAnim.cancelAnim();//取消动画

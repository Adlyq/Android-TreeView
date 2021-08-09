# Android-TreeView

一个简单的TreeView实现

## 关键文件

TreeAdapter

## 实现原理

TreeAdapter继承自RecyclerView.Adapter，维护两个List\<Node\<T\>\>，allNodes存储所有Node，revNodes存储要显示的Node

## 使用方法

```Kotlin
val rv: RecyclerView = xxx
val mAdapter = SimpleTreeAdapter()

rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mAdapter.apply {

            val n01 = addNode(root, "n01", true)?:throw Exception()
            val n02 = addNode(root, "n02", true)?:throw Exception()
            val n03 = addNode(root, "n03", true)?:throw Exception()

            val n11 = addNode(n01, "n11", true)?:throw Exception()
            addNode(n01, "n12")?:throw Exception()

            addNode(n02, "n21")?:throw Exception()
            addNode(n02, "n22", true)?:throw Exception()

            addNode(n03, "n31")?:throw Exception()

            addNode(n11, "n111")?:throw Exception()

        }

        mAdapter.flash()

        #addNode(父节点， 附带资源， 是否可展开， 是否展开)：新节点
```

## 显示效果

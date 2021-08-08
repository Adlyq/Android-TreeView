package com.adlyq.test.atv

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class TreeAdapter<T, H: TreeAdapter.ViewHolder>: RecyclerView.Adapter<H>() {
    init {
        Node.onChildChange = object: Node.OnChildChange{
            override fun onAdd(child: Node<Any>) {
                allNodes.add(child as Node<T>)
            }

            override fun onRemove(child: Node<Any>) {
                allNodes.remove(child as Node<T>)
            }
        }
    }

    open class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    abstract class BaseTreeNode<R>(var parent: BaseTreeNode<R>?, var src: R? = null,
                                   val explainable: Boolean = false, explained: Boolean = true) {
         val level: Int
            get() = (parent?.level?:-2)+1

        var explained = explained
            get() = if (explainable) field else false

        protected open val mChildren = mutableListOf<BaseTreeNode<R>>()

        val children    get() = mChildren as List<BaseTreeNode<R>>
    }

    private class Node<E>(parent: Node<E>?, src: E? = null, explainable: Boolean = false,
                          explained: Boolean = true): BaseTreeNode<E>(parent, src, explainable, explained){
        interface OnChildChange{
            fun onAdd(child: Node<Any>)
            fun onRemove(child: Node<Any>)
        }

        fun addChild(child: Node<E>) = if (explainable){
            mChildren.add(child).also {
                child.parent = this
                onChildChange?.onAdd(child as Node<Any>) }
        }else false

        fun removeChild(child: Node<E>) =
            if (mChildren.remove(child)) {
                child.parent = null
                onChildChange?.onRemove(child as Node<Any>)
                true
            } else false

        fun removeAll(){
            mChildren.forEach { onChildChange?.onRemove(it as Node<Any>) }
            mChildren.clear()
        }

        companion object{
            var onChildChange: OnChildChange? = null
        }
    }

    private val mRoot = Node<T>(null, null, true)

    val root    get() = mRoot as BaseTreeNode<T>

    private val allNodes = mutableListOf<Node<T>>()

    private val revNodes = mutableListOf<Node<T>>()

    val nodes   get() = revNodes as List<BaseTreeNode<T>>

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = revNodes.size

    fun addNode(parent: BaseTreeNode<T> = mRoot, src: T? = null,
                explainable: Boolean = false, explained: Boolean = true): BaseTreeNode<T>?{
        val node = Node(parent as Node, src, explainable, explained)
        return if (parent.addChild(node)) {
            allNodes.add(node)
            node
        }else null
    }

    fun removeNode(node: BaseTreeNode<T>) =  if (allNodes.remove(node as Node<T>)) {
        if (node.parent?.explainable == true) (node.parent as Node).removeChild(node)
        true
    } else false

    fun explainAll(){
        allNodes.forEach{
            it.explained = true
        }
        revNodes.clear()
        revNodes.addAll(allNodes)
        notifyDataSetChanged()
    }

    fun collapse(){
        allNodes.forEach{
            it.explained = false
        }
        revNodes.clear()
        revNodes.addAll(mRoot.children as List<Node<T>>)
    }

    fun flash(){
        revNodes.apply {
            clear()
            addAll(castTo(mRoot))
        }
        notifyDataSetChanged()
        //TODO("优化算法，使用更具体的刷新方式")
    }

    private fun castTo(root: Node<T>): List<Node<T>>{
        val lst = mutableListOf<Node<T>>()
        root.children.sortedBy { !it.explainable }.forEach{
            lst.add(it as Node)//Error
            if (it.explained)
                lst.addAll(castTo(it))
        }
        return lst
    }
}
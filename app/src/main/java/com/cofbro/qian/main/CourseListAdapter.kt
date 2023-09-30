package com.cofbro.qian.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cofbro.qian.R
import com.cofbro.qian.databinding.ItemCourseListBinding
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getJSONArrayExt
import com.cofbro.qian.utils.getStringExt

class CourseListAdapter : RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
    private var data: JSONObject? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseListBinding.inflate(inflater, parent, false)
        return CourseListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        data?.getJSONArray("channelList")?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CourseListViewHolder(private val binding: ItemCourseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            data?.getJSONArray("channelList")?.get(position)?.let {
                // 数据下发的item
                val jsonObject = (it as? JSONObject)
                // cpi，后面签到需用此参数
                val cpi = jsonObject?.getStringExt("cpi")
                // 班级id
                val classId = jsonObject?.getStringExt("key")
                // 学生人数
                val studentCount = jsonObject?.getStringExt("content.studentcount")
                // 班级名称
                val className = jsonObject?.getStringExt("content.name")
                val itemArray = jsonObject?.getJSONArrayExt("content.course.data")
                if (itemArray?.size != 0) {
                    val item = (itemArray?.get(0) as? JSONObject)
                    // 课程id
                    val courseId = item?.getStringExt("id")
                    // 课程名称
                    val courseName = item?.getStringExt("name")
                    // 学校名称
                    val school = item?.getStringExt("schools", "未设置学校")
                    // 老师
                    val teacherName = item?.getStringExt("teacherfactor")

                    // 绑定数据
                    binding.tvCourseId.text = courseId
                    binding.tvClassName.text = courseName
                    binding.tvClassId.text = classId
                    binding.tvSchoolName.text = school
                    binding.tvStudentCount.text = studentCount
                    binding.tvTeacherName.text = teacherName
                    val options = RequestOptions().transform(
                        CenterCrop(),
                        RoundedCorners(dp2px(binding.root.context, 5))
                    )
                    Glide.with(binding.root)
                        .load(item?.getStringExt("imageurl"))
                        .apply(options)
                        .into(binding.ivClassAvtar)
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(t: JSONObject) {
        data = t
        notifyDataSetChanged()
    }
}
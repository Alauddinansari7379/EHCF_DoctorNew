package com.example.ehcf_doctor.MyPatient.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ehcf_doctor.MyPatient.activity.PatientHistory
import com.example.ehcf_doctor.MyPatient.activity.PatientReport
import com.example.ehcf_doctor.MyPatient.model.ModelMyPatient
import com.example.ehcf_doctor.R
import java.text.SimpleDateFormat
import java.util.*


class AdapterMyPatient(
    val context: Context, private val list: ModelMyPatient,    val comment:CommentList
) :
    RecyclerView.Adapter<AdapterMyPatient.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_row_my_patient, parent, false)
        )
    }
    var currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        holder.name.text = list.result[position].customer_name
        holder.mobileNumber.text = list.result[position].phone_number
        holder.email.text = list.result[position].email
        //  holder.doctorName.text = list.result[position].n.toString()

        if (list.result[position].overall_ratings!=null){
            holder.ratingBar.rating= list.result[position].overall_ratings.toFloat()
            holder.overAllRating.text= list.result[position].overall_ratings
            holder.tvNoOfrating.text= list.result[position].no_of_ratings
        }

        holder.viewHistory.setOnClickListener {
            val intent = Intent(context as Activity, PatientHistory::class.java)
                .putExtra("Id", list.result[position].id.toString())
            context.startActivity(intent)
        }

        holder.tvRatingReview.setOnClickListener {
            comment.commentList(list.result[position].id)
        }
        holder.viewReport.setOnClickListener {
            val intent = Intent(context as Activity, PatientReport::class.java)
                .putExtra("Id", list.result[position].id.toString())
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return list.result.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvNameMyPatient)
        val mobileNumber: TextView = itemView.findViewById(R.id.tvMobileNumberMyPatient)
        val email: TextView = itemView.findViewById(R.id.tvEmailMyPatient)
        val overAllRating: TextView = itemView.findViewById(R.id.tvOverAllRating)
        val tvNoOfrating: TextView = itemView.findViewById(R.id.tvNoOfrating)
        val tvRatingReview: TextView = itemView.findViewById(R.id.tvRatingReview)
        val cardView: CardView = itemView.findViewById(R.id.cardViewPre)
        val viewHistory: Button = itemView.findViewById(R.id.btnViewHistory)
        val viewReport: Button = itemView.findViewById(R.id.btnViewReports)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarDProfile)

    }
    interface CommentList {
        fun commentList(id: String)
    }
}
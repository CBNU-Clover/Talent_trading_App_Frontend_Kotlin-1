package com.example.talent_trading_market_kt.pointfunction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.pointfunction.pointDTO.ChargePointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChargePointActivity  : AppCompatActivity() {
    lateinit var charge_button:Button
    lateinit var point_amount:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointcharge)
        charge_button=findViewById(R.id.charge_button)
        point_amount=findViewById(R.id.point_amount)
        val service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)

        charge_button.setOnClickListener {
            val point_amount=point_amount.text.toString()
            val chargePointDTO = ChargePointDTO()
            chargePointDTO.point=point_amount.toLong()
            if (service != null) {
                service.charge_point(chargePointDTO).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ChargePointActivity, "충전 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ChargePointActivity, MyPointActivity::class.java)
                            finish()
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this@ChargePointActivity, "충전 실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@ChargePointActivity, "다시 충전 버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                    }

                })
            }


        }
    }
}
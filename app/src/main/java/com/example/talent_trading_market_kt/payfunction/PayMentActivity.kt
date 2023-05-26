package com.example.talent_trading_market_kt.payfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.pointfunction.PointFunctionApi
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_trade.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PayMentActivity : AppCompatActivity() {
    lateinit var payment:Button
    lateinit var pay_title:TextView
    lateinit var current_point:TextView
    lateinit var final_point:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade)
        payment=findViewById(R.id.payment)
        pay_title=findViewById(R.id.pay_title)
        current_point=findViewById(R.id.current_point)
        final_point=findViewById(R.id.final_point)
        if(intent.hasExtra("pay_title"))
        {
            pay_title.text=intent.getStringExtra("pay_title")

        }
        if(intent.hasExtra("pay_price"))
        {

            board_first_price.text=intent.getStringExtra("pay_price")
            board_final_price.text=intent.getStringExtra("pay_price")
            println(board_final_price.text)

        }
        val service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)
        if (service != null) {
            service.show_point().enqueue(object : Callback<ShowPointDTO> {
                override fun onResponse(call: Call<ShowPointDTO>, response: Response<ShowPointDTO>) {
                    if (response.isSuccessful) {
                        var result:Long
                        var showPointDTO= ShowPointDTO()
                        showPointDTO= response.body()!!
                        current_point.text= showPointDTO.point.toString()+"원"
                        val boardFinalPriceString = board_final_price.text.toString().replace("원", "")
                        val boardFinalPriceLong= boardFinalPriceString.toLongOrNull()
                        if (boardFinalPriceLong != null) {
                            result= showPointDTO.point!! - boardFinalPriceLong
                            final_point.text = result.toString() + "원"
                        } else {

                            // 문자열을 Long으로 변환할 수 없는 경우에 대한 처리
                        }

                    }
                }

                override fun onFailure(call: Call<ShowPointDTO>, t: Throwable) {
                }

            })
        }



        payment.setOnClickListener {
            //결제가 이루어진다.
        }
    }
}
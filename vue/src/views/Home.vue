<template>
    <div>
        <div style="padding-bottom: 60px;">
            <el-row>
                <el-col :span="6" style="padding-left: 10px;">
                    <el-card style="color: #409EFF;">
                        <div><i class="el-icon-user-solid"></i> 用户总数</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold;">
                            4000
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 10px;">
                    <el-card style="color:#F56C6C" >
                        <div><i class="el-icon-s-marketing"></i> 销售总量</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold;">
                            $ 10000000
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 10px;">
                    <el-card style="color:#67C23A">
                        <div><i class="el-icon-wallet"></i> 收益总额</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold;">
                            $ 130000
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 10px;">
                    <el-card style="color:#E6A23C">
                        <div><i class="el-icon-s-shop"></i> 门店总数</div>
                        <div style="padding: 10px 0; text-align: center; font-weight: bold;">
                            80000
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    
    
        <el-row style>
            <el-col :span="12">
                <div id="main" style="width: 500px; height: 400px"></div>
            </el-col>
            <el-col :span="12">
                <div id="pie" style="width: 550px; height: 450px"></div>
            </el-col>
        </el-row>
       
    </div>
</template>


<script>

import * as echarts from 'echarts';

export default{
    name: 'Home',
    data(){
        return{

        }
    },

    mounted() { //页面元素渲染之后再触发

        //柱状图折线图
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        var option = {
            title: {
                text: '各季度会员人数增长变化图',
                subtext: '2022',
                left: 'center'
            },
            xAxis: {
                type: 'category',
                data: ['第一季度','第二季度','第三季度','第四季度']
            },
            yAxis: {
                type: 'value'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '星巴克',
                    data: [],
                    type: 'line'
                },
                {
                    name: '星巴克',
                    data: [],
                    type: 'bar'
                },
                {
                    name: '瑞幸',
                    data: [],
                    type: 'bar'
                },
                {
                    name: '瑞幸',
                    data: [],
                    type: 'line'
                },
            ]
        };

        this.request.get('/echarts/members').then(res => {
            // option.xAxis.data = res.data.x;
            option.series[0].data = res.data;
            option.series[1].data = res.data;
            option.series[2].data = [5,6,7,8];
            option.series[3].data = [5,6,7,8];
            myChart.setOption(option);
        })
       



        //饼图
        var chartDomPie = document.getElementById('pie');
        var pieMyChart = echarts.init(chartDomPie);

        var pieOption = {
            title: {
                text: '各季度会员人数增长统计图',
                subtext: '2022',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '星巴克',
                    type: 'pie',
                    center: ['35%','60%'],
                    radius: '40%',
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}: {c}({d}%)'
                        }
                    },

                    data: [
                        // { value: 1048, name: 'Search Engine' },
                        // { value: 735, name: 'Direct' },
                        // { value: 580, name: 'Email' },
                        // { value: 484, name: 'Union Ads' },
                        // { value: 300, name: 'Video Ads' }
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },
                {
                    name: '瑞幸',
                    type: 'pie',
                    center: ['75%','40%'],
                    radius: '35%',
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}: {c}({d}%)'
                        }
                    },

                    data: [
                        // { value: 1048, name: 'Search Engine' },
                        // { value: 735, name: 'Direct' },
                        // { value: 580, name: 'Email' },
                        // { value: 484, name: 'Union Ads' },
                        // { value: 300, name: 'Video Ads' }
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        
        this.request.get("/echarts/members").then(res => {
            pieOption.series[0].data = [
                {value: res.data[0], name: '第一季度'},
                {value: res.data[1], name: '第二季度'},
                {value: res.data[2], name: '第三季度'},
                {value: res.data[3], name: '第四季度'},
            ];
            pieOption.series[1].data = [
                {value: 5, name: '第一季度'},
                {value: 6, name: '第二季度'},
                {value: 7, name: '第三季度'},
                {value: 8, name: '第四季度'},
            ];
            pieMyChart.setOption(pieOption);
        })
       
        
        
    },


}

</script>
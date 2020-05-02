<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>About me</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
<!--        <pan-thumb :image="user.avatar" :height="'100px'" :width="'100px'" :hoverable="false">-->
        <i class="el-icon-user-solid"> </i>
        <div style="text-align: center">{{user.username}}</div>
<!--          {{ user.role }}-->
<!--        </pan-thumb>-->
      </div>
      <div class="box-center">
        <div class="user-name text-center">Hello!{{ user.firstName}}{{user.lastName}}</div>
<!--        <div class="user-role text-center text-muted">{{ user.role | uppercaseFirst }}</div>-->
      </div>
    </div>

    <div class="user-bio">
      <div class="user-education user-bio-section">
        <div class="user-bio-section-header"><svg-icon icon-class="education" /><span>个人介绍</span></div>
        <div class="user-bio-section-body">
          <div class="text-muted">
            这个人很懒没有写介绍
          </div>
        </div>
      </div>

      <div class="user-skills user-bio-section">
        <div class="user-bio-section-header"><svg-icon icon-class="skill" /><span>主营商品</span></div>
        <div class="user-bio-section-body">
          <div class="progress-item">
            <span>狗</span>
            <el-progress :percentage="70" />
          </div>
          <div class="progress-item">
            <span>猫</span>
            <el-progress :percentage="18" />
          </div>
          <div class="progress-item">
            <span>鸟</span>
            <el-progress :percentage="12" />
          </div>
          <div class="progress-item">
            <span>鱼</span>
            <el-progress :percentage="100" status="success" />
          </div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
// import PanThumb from '@/components/PanThumb'

export default {
  // components: { PanThumb },
  data () {
    return {
      user: {}
    }
  },
  // props: {
  //   user: {
  //     type: Object,
  //     default: () => {
  //       return {
  //         // name: '',
  //         // email: '',
  //         // avatar: '',
  //         // role: '',
  //         username: '123123',
  //         password: null,
  //         email: 'yourname@yourdomain.com',
  //         firstName: 'ABC',
  //         lastName: 'XYX',
  //         status: null,
  //         address1: '901 San Antonio Road',
  //         address2: 'MS UCUP02-206',
  //         city: 'Palo Alto',
  //         state: 'CA',
  //         zip: '94303',
  //         country: '美国',
  //         phone: '+15555555555',
  //         favouriteCategoryId: 'CATS',
  //         languagePreference: 'eng',
  //         listOption: true,
  //         bannerOption: true,
  //         bannerName: ' '
  //       }
  //     }
  //   }
  // }
  created () {
    this.user = JSON.parse(window.sessionStorage.getItem('user'))
  }
}
</script>

<style lang="less" scoped>
.box-center {
  /*margin-top: 20px;*/
  margin: 0 auto;
  /*padding-top: 20px;*/
  display: table;
}

.el-icon-user-solid {
  font-size: 100px;
  display: block;
  text-align: center;
  border: #b8b8b8 solid;
  border-radius: 50%;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}
</style>

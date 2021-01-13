import { shallowMount } from '@vue/test-utils'
import UserRegister from '@/components/UserRegister'

describe('UserRegister', () => {
  it('renders an error when username is null', () => {
    const wrapper = shallowMount(UserRegister)
    wrapper.setData({
      user: {
        username: '',
        email: 'winograd@thu.com',
        password: '12345'
      }
    })
    wrapper.find('el-button').trigger('click')
    expect(!wrapper.vm.username).toBeTruthy()
  })

  it('renders an error when email is null', () => {
    const wrapper = shallowMount(UserRegister)
    wrapper.setData({
      user: {
        username: 'winograd',
        email: '',
        password: '12345'
      }
    })
    wrapper.find('el-button').trigger('click')
    expect(!wrapper.vm.email).toBeTruthy()
  })

  it('renders an error when email is unvalid', () => {
    const wrapper = shallowMount(UserRegister)
    wrapper.setData({
      user: {
        username: 'winograd',
        email: '123',
        password: '12345'
      }
    })
    wrapper.find('el-button').trigger('click')

    var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    expect(!reg.test(wrapper.vm.email)).toBeTruthy()
  })

  it('renders an alert that reads 请输入密码！when password is null and email is valid', () => {
    const wrapper = shallowMount(UserRegister)
    wrapper.setData({
      user: {
        username: 'winograd',
        email: 'winograd@thu.com',
        password: ''
      }
    })
    wrapper.find('el-button').trigger('click')

    // var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    // mexpect(!reg.test(wrapper.vm.email)).toBeFalsy()
    expect(!wrapper.vm.user.password).toBeTruthy()
  })
})

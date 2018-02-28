package utils

import com.typesafe.config.{Config, ConfigFactory}

trait Configuration {
  def config: Config
}

trait ConfigurationModuleImpl extends Configuration {
  private val internalConfig: Config = {
    ConfigFactory.load(this.getClass.getClassLoader, "application.conf")
  }
  
  def config: Config = internalConfig
}
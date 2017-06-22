package com.github.virtualstack.repositories

import slick.driver.JdbcProfile
import play.api.db.slick.HasDatabaseConfigProvider

/**
  * Trait that contains generic slick db handling code to be mixed in with DAOs
  */
trait RepositorySlick extends DBTableDefinitions with HasDatabaseConfigProvider[JdbcProfile]

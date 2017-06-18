package com.github.virtualstack.models

case class Repository(name: String, tag: Option[Tag], tags: Option[List[Tag]])

package com.company.sportHubPortal.Models;

import net.bytebuddy.utility.RandomString;

public class VerifyLink implements GenerationLinkStrategy {
  @Override
  public String generateLink() {
    return RandomString.make(64);
  }
}

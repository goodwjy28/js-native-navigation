//
//  BotsStoreJSExportImpl.h
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HCStoreJSExport.h"
#import "HCJSCoreBaseViewController.h"
NS_ASSUME_NONNULL_BEGIN

@interface HCStoreJSExportImpl : NSObject <HCStoreJSExport>

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext;

@end

NS_ASSUME_NONNULL_END
